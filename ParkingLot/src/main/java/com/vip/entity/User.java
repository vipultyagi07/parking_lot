package com.vip.entity;

import com.vip.entity.baseEntity.BaseEntity;
import jakarta.persistence.Entity;
import lombok.Data;


@Entity
@Data
public class User extends BaseEntity {

//
//    @NotBlank
    private String name;

    private String address;

    private String mobileNo;

//    @NotBlank
//    @Email
//    @Size(max = 255)
    private String email;
//
//    @NotBlank
//    @Size(max = 10)
    private String gender;
    private String passwordHash;
    private String otp;

}
