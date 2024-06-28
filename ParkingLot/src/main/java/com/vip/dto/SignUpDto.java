package com.vip.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class SignUpDto {

    @NonNull
    private String name;

    @NonNull
    private String address;

    @NonNull
    private String mobileNo;

    @NonNull
    private String email;

    @NonNull
    private String gender;

    @NonNull
    private String password;

}
