package com.vip.common.utility;

import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class ValidationUtility {


    public static boolean isValidOtp(Date otpTime) {
        if(Objects.isNull(otpTime)){
            return false;
        }
        Date currentTime = new Date();
        long diffInMillies = currentTime.getTime() - otpTime.getTime();
        long diffInMinutes = TimeUnit.MILLISECONDS.toMinutes(diffInMillies);
        return diffInMinutes < 5;
    }

}

