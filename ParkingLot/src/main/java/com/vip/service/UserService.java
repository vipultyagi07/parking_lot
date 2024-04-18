package com.vip.service;


import com.vip.common.enums.LoginAndSignupStatus;
import com.vip.common.utility.Utils;
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
    private PasswordEncoderService passwordEncoderService;

    public ResponseEntity<SignUpResponseDto> saveTheDataOfNewUser(SignUpDto signUpDto) throws Exception {
        SignUpResponseDto signUpResponseDto= new SignUpResponseDto();

        Optional<User> existingUser = userRepo.findByEmail(signUpDto.getEmail());
        if(existingUser.isPresent()){
            throw new ParkingLotException("User already present, please sign in",ErrorCode.USER_IS_ALREADY_PRESENT);
        }
        Optional<User> existingUserMobile = userRepo.findTop1ByMobileNo(signUpDto.getMobileNo());
        if(existingUserMobile.isPresent()){
            throw new ParkingLotException("User already present, please sign in",ErrorCode.USER_IS_ALREADY_PRESENT);

        }

        User newUser= new User();
        newUser.setName(signUpDto.getName());
        newUser.setEmail(signUpDto.getEmail());
        newUser.setCreatedDate(new Date());
        newUser.setAddress(signUpDto.getAddress());
        newUser.setMobileNo(signUpDto.getMobileNo());
        newUser.setGender(signUpDto.getGender());
        // first we encode the password and save it to the DB
        String secretKey = Utils.SECRET_KEY;
        String encryptPassword = passwordEncoderService.encodePassword(signUpDto.getPassword(),secretKey);

        
        newUser.setPasswordHash(encryptPassword);

        User createdUser = userRepo.save(newUser);
        signUpResponseDto.setUser(createdUser);
        signUpResponseDto.setSignUpStatus(LoginAndSignupStatus.USER_CREATED_SUCCESSFULLY.getDisplayName());
        return new ResponseEntity<>(signUpResponseDto,HttpStatus.OK);


    }

    public ResponseEntity<LoginResponseDto>  loginExistingUser(LoginDto loginDto) throws Exception {

        String secretKey = Utils.SECRET_KEY;

        String email = loginDto.getEmail();

       Optional<User> user= userRepo.findTop1ByEmail(email);
       if(!user.isPresent()){
           throw new ParkingLotException("No such email found, please signup",ErrorCode.USER_IS_NOT_PRESENT);
       }
       else{
          try {
              String encryptPassword = user.get().getPasswordHash();
              String originalPassword = passwordEncoderService.verifyPassword(encryptPassword, secretKey);
              if (loginDto.getPassword().equals(originalPassword)) {
                  LoginResponseDto loginResponseDto= new LoginResponseDto();
                  loginResponseDto.setEmail(loginDto.getEmail());
                  loginResponseDto.setLoginStatus(LoginAndSignupStatus.LOGIN_SUCCESSFUL.getDisplayName());
                  return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);

              } else{
                  throw new ParkingLotException("incorrect password",ErrorCode.INCORRECT_PASSWORD);
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

    public  ResponseEntity<String>  sendOTP(String phoneNumber) {
        // Generate a fake OTP (replace this with a real OTP generation logic)
        String otp = generateOTP();

        // Send the OTP via SMS (replace this with your SMS gateway integration)   // this is important
        // sendOTPViaSMS(phoneNumber, otp);

        // Store the OTP and user phone number for verification (in-memory, database, etc.)
        storeOTP(phoneNumber, otp);

        return ResponseEntity.ok("OTP sent successfully");    }

//    private void sendOTPViaSMS(String phoneNumber, String otp) {
//
//       String response= messageService.sendOtpViaSms(phoneNumber,otp);
//    }

    private void storeOTP(String phoneNumber, String otp) {

        User existingUser = userRepo.findByMobileNo(phoneNumber).get();
        if(existingUser==null){
            return;
        }
        existingUser.setLastModifiedDate(new Date());
         existingUser.setOtp(otp);

         userRepo.save(existingUser);

    }

    private String generateOTP() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder(Utils.OTP_LENGTH);

        for (int i = 0; i < Utils.OTP_LENGTH; i++) {
            int randomIndex = random.nextInt(Utils.OTP_CHARACTERS.length());
            otp.append(Utils.OTP_CHARACTERS.charAt(randomIndex));
        }

        return otp.toString();
    }


    public ResponseEntity<Object> verifyOTP(String phoneNumber, String otp) {

        User user = userRepo.findByMobileNo(phoneNumber).get();
        String userOtp = user.getOtp();
        if(userOtp.equals(otp)){
            return new ResponseEntity<>("Otp verification succesfull",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>("Entered Otp is incorrect",HttpStatus.NOT_FOUND);
        }

    }

    public ResponseEntity<Object> resetPassword(String phoneNumber, String newPassword) throws Exception {

        User existingUser = userRepo.findTop1ByMobileNo(phoneNumber).get();

        String encodePassword = passwordEncoderService.encodePassword(newPassword, Utils.SECRET_KEY);
        existingUser.setPasswordHash(encodePassword);
        existingUser.setLastModifiedDate(new Date());

        userRepo.save(existingUser);

        return new ResponseEntity<>("Password Changed Sucessfully",HttpStatus.ACCEPTED);





    }
}
