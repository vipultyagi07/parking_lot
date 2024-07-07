package com.vip.controller;

import com.vip.dto.LoginDto;
import com.vip.dto.LoginResponseDto;
import com.vip.security.JwtUtil;
import com.vip.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
public class JwtAuthenticationController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService; // Service containing custom login logic

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<LoginResponseDto> createAuthenticationToken(@RequestBody LoginDto authenticationRequest) throws Exception {
        // Authenticate using custom method
        LoginResponseDto loginResponseDto = userService.loginExistingUser(authenticationRequest).getBody();

        // Generate JWT token
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtUtil.generateToken(userDetails.getUsername());

        // Set token in response
        loginResponseDto.setJwtToken(token);

        return ResponseEntity.ok(loginResponseDto);
    }
}
