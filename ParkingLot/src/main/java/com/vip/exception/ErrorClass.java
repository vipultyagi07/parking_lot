package com.vip.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

public class ErrorClass {
    @JsonProperty
    private HttpStatus httpStatus;

    @JsonProperty
    private String errorCode;
    @JsonProperty
    private String errorMessage;
    @JsonProperty
    private String errorStackTrace;



    public ErrorClass(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ErrorClass(HttpStatus httpStatus, String errorCode, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
    public ErrorClass(HttpStatus httpStatus, String errorCode, String errorMessage,String errorStackTrace) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.errorStackTrace=errorStackTrace;
    }

    // getters for errorCode and errorMessage
}
