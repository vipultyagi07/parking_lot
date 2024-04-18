package com.vip.dto;

import com.vip.entity.User;
import lombok.Data;

@Data
public class SignUpResponseDto {
    private User user;
    private String signUpStatus;
}
