package com.vip.service;


import com.vip.common.constants.Constants;
import com.vip.common.enums.LoginAndSignupStatus;
import com.vip.common.utility.Utils;
import com.vip.common.utility.ValidationUtility;
import com.vip.dto.*;
import com.vip.entity.User;
import com.vip.exception.ErrorCode;
import com.vip.exception.ParkingLotException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Optional;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private EmailService emailService;


    @Autowired
    private CryptoService cryptoService;

    public ResponseEntity<SignUpResponseDto> saveTheDataOfNewUser(SignUpDto signUpDto) throws Exception {
        SignUpResponseDto signUpResponseDto = new SignUpResponseDto();

        Optional<User> existingUser = userRepo.findByEmail(signUpDto.getEmail());
        if (existingUser.isPresent()) {
            throw new ParkingLotException("Email is already registered, please sign in", ErrorCode.USER_IS_ALREADY_PRESENT);
        }
        Optional<User> existingUserMobile = userRepo.findTop1ByMobileNo(signUpDto.getMobileNo());
        if (existingUserMobile.isPresent()) {
            throw new ParkingLotException("Mobile is already registered, please sign in", ErrorCode.USER_IS_ALREADY_PRESENT);

        }

        User newUser = new User();
        newUser.setName(signUpDto.getName());
        newUser.setEmail(signUpDto.getEmail());
        newUser.setCreatedDate(new Date());
        newUser.setAddress(signUpDto.getAddress());
        newUser.setMobileNo(signUpDto.getMobileNo());
        newUser.setGender(signUpDto.getGender());
        // first we encode the password and save it to the DB

        newUser.setPassword(cryptoService.encryptData(signUpDto.getPassword()));

        User createdUser = userRepo.save(newUser);
        signUpResponseDto.setUser(createdUser);
        signUpResponseDto.setSignUpStatus(LoginAndSignupStatus.USER_CREATED_SUCCESSFULLY.getDisplayName());
        return new ResponseEntity<>(signUpResponseDto, HttpStatus.OK);


    }

    public ResponseEntity<LoginResponseDto> loginExistingUser(LoginDto loginDto) throws Exception {


        String email = loginDto.getEmail();

        Optional<User> user = userRepo.findTop1ByEmail(email);
        if (!user.isPresent()) {
            throw new ParkingLotException("No such email found, please register", ErrorCode.USER_IS_NOT_PRESENT);
        } else {
            try {
                String encryptPassword = user.get().getPassword();
                String originalPassword = cryptoService.decryptData(encryptPassword);
                if (loginDto.getPassword().equals(originalPassword)) {
                    LoginResponseDto loginResponseDto = new LoginResponseDto();
                    loginResponseDto.setEmail(loginDto.getEmail());
                    loginResponseDto.setLoginStatus(LoginAndSignupStatus.LOGIN_SUCCESSFUL.getDisplayName());
                    return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);

                } else {
                    throw new ParkingLotException("incorrect password", ErrorCode.INCORRECT_PASSWORD);
                }
            } catch (ParkingLotException ex) {
                // Handle the custom exception, log the error, or perform other actions
                log.error("Parking Lot Exception: " + ex.getErrorCode() + " - " + ex.getMessage(), ex);
                throw ex;
            } catch (Exception ex) {
                log.error("Unexpected error: " + ex.getMessage(), ex);
                throw new ParkingLotException("Internal Server Error", ErrorCode.INTERNAL_SERVER_ERROR);
            }

        }
    }

    public ResponseEntity<String> sendOTP(String toEmail) throws Exception {

        Optional<User> existingUser = userRepo.findByEmail(toEmail);
        if (!existingUser.isPresent()) {
            throw new ParkingLotException("No such email found, please register", ErrorCode.USER_IS_NOT_PRESENT);
        }
        User user = existingUser.get();
        String otp;
        boolean validOtp = ValidationUtility.isValidOtp(user.getOtpTime());

        if (!validOtp) {
            otp = generateOTP(toEmail);
            storeOTP(user, otp);
        }
        else {
            otp = cryptoService.decryptData(user.getOtp());
        }

        sendOTPViaEmail(toEmail, otp, Constants.OTP_TEMPLATE);

        return ResponseEntity.ok("OTP sent successfully");
    }

    private void sendOTPViaEmail(String toEmail, String otp,String templateName) throws Exception {

        EmailDetails emailDetails = new EmailDetails();
        emailDetails.setSubject("OTP for reset password");
        emailDetails.setRecipient(toEmail);
        emailDetails.setMsgBody(otp);

        emailService.sendSimpleMail(emailDetails,templateName);
    }

    private void storeOTP(User user, String otp) throws Exception {


        user.setLastModifiedDate(new Date());
        user.setOtp(cryptoService.encryptData(otp));
        user.setOtpTime(new Date());

        userRepo.save(user);

    }

    private String generateOTP(String toEmail) {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder(Utils.OTP_LENGTH);

        for (int i = 0; i < Utils.OTP_LENGTH; i++) {
            int randomIndex = random.nextInt(Utils.OTP_CHARACTERS.length());
            otp.append(Utils.OTP_CHARACTERS.charAt(randomIndex));
        }

        return otp.toString();
    }


    public ResponseEntity<Object> verifyOTP(String email, String otp) throws Exception {

        Optional<User> userOptional = userRepo.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new ParkingLotException("Please resend the OTP" ,ErrorCode.OTP_NOT_FOUND);
        }
        User user=userOptional.get();
        String userOtp = cryptoService.decryptData(user.getOtp());
        if (userOtp.equals(otp)) {
            return new ResponseEntity<>("Otp verified successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Entered Otp is incorrect", HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<Object> resetPassword(String phoneNumber, String newPassword) throws Exception {

        User existingUser = userRepo.findTop1ByMobileNo(phoneNumber).get();

        String encodePassword = cryptoService.encryptData(newPassword);
        existingUser.setPassword(encodePassword);
        existingUser.setLastModifiedDate(new Date());

        userRepo.save(existingUser);

        return new ResponseEntity<>("Password Changed Successfully", HttpStatus.ACCEPTED);

    }
}
