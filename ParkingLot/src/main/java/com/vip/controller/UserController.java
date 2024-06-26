package com.vip.controller;

import com.vip.dto.LoginDto;
import com.vip.dto.LoginResponseDto;
import com.vip.dto.SignUpDto;
import com.vip.dto.SignUpResponseDto;
import com.vip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign/up")
    public ResponseEntity<SignUpResponseDto> signUpUser(@RequestBody SignUpDto signUpDto) throws Exception {

        return userService.saveTheDataOfNewUser(signUpDto);

    }
    @PostMapping("/sign/in")
    public ResponseEntity<LoginResponseDto> signInUser(@RequestBody LoginDto loginDto) throws Exception {
        return userService.loginExistingUser(loginDto);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> sendOTP(@RequestParam String toEmail) throws Exception {
        // Generate and send OTP logic here
         userService.sendOTP(toEmail);
        return new ResponseEntity<>("Otp sent successfully", HttpStatus.OK);

    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Object> verifyOTP(@RequestBody Map<String, String> otpData) throws Exception {
        // Verify OTP logic here
        return userService.verifyOTP(otpData.get("email"), otpData.get("otp"));

    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@RequestBody Map<String, String> resetData) throws Exception {
        // Reset password logic here
        return userService.resetPassword(resetData.get("email"), resetData.get("newPassword"));
    }


    // Other user-related endpoints (e.g., profile update, login, etc.)
}
