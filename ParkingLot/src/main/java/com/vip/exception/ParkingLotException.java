package com.vip.exception;


import org.springframework.http.HttpStatus;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ParkingLotException extends RuntimeException {
    private String errorCode;
    private HttpStatus httpStatus;
    private String errorStackTrace;



    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getErrorStackTrace() {
        return errorStackTrace;
    }

    public ParkingLotException() {
    }

    public ParkingLotException(Exception e) {
    }


    public ParkingLotException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;

    }

    public ParkingLotException(Object message, String errorCode, HttpStatus httpStatus) {
        super(message.toString());
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;

    }

    public ParkingLotException(Object message, String errorCode, HttpStatus httpStatus, Exception e) {
        super(message.toString());
        this.errorCode = errorCode;
        this.httpStatus = httpStatus;
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String stackTrace = sw.toString();
        this.errorStackTrace = stackTrace;


    }
}
