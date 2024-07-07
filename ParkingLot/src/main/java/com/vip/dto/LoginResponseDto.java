package com.vip.dto;

import lombok.Data;

@Data
public class LoginResponseDto {

    private String email;
    private Long userId;
    private String loginStatus;
    private String jwtToken;
}
