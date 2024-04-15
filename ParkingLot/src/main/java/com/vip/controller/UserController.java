package com.vip.controller;

import com.vip.dto.LoginDto;
import com.vip.dto.SignUpDto;
import com.vip.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/sign/up")
    public ResponseEntity<Object> signUpUser(@RequestBody SignUpDto signUpDto) throws Exception {

        return userService.saveTheDataOfNewUser(signUpDto);

    }
    @PostMapping("/sign/in")
    public String signInUser(@RequestBody LoginDto loginDto) throws Exception {

        return userService.loginExistingUser(loginDto);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> sendOTP(@RequestParam String phoneNumber) {
        // Generate and send OTP logic here
        return userService.sendOTP(phoneNumber);
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<Object> verifyOTP(@RequestBody Map<String, String> otpData) {
        // Verify OTP logic here
        return userService.verifyOTP(otpData.get("phoneNumber"), otpData.get("otp"));

    }

    @PostMapping("/reset-password")
    public ResponseEntity<Object> resetPassword(@RequestBody Map<String, String> resetData) throws Exception {
        // Reset password logic here
        return userService.resetPassword(resetData.get("phoneNumber"), resetData.get("newPassword"));
    }


    // Other user-related endpoints (e.g., profile update, login, etc.)
}
