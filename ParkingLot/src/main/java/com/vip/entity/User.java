package com.vip.entity;

import com.vip.entity.baseEntity.BaseEntity;
import jakarta.persistence.Entity;

import java.util.Date;


@Entity
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
    private String password;
    private String otp;
    private Date otpTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public Date getOtpTime() {
        return otpTime;
    }

    public void setOtpTime(Date otpTime) {
        this.otpTime = otpTime;
    }
}
