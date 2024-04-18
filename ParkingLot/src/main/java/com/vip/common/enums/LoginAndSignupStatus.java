package com.vip.common.enums;

public enum LoginAndSignupStatus {

    LOGIN_SUCCESSFUL("Login Successful"),
    USER_CREATED_SUCCESSFULLY("User Created Successully"),
    INCORRECT_PASSWORD("Incorrect Password"),
    USER_IS_NOT_PRESENT("User is not present, Please Sign Up"),
    USER_IS_ALREADY_PRESENT("User already present, please sign in");
    private final String displayName;

    LoginAndSignupStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
