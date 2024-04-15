package com.vip.exception;

import com.vip.common.errorResponse.ErrorClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorClass> handleRuntimeException(RuntimeException ex) {
        if (ex instanceof ParkingLotException) {
            // Handle ParkingLotException
            ParkingLotException parkingLotException = (ParkingLotException) ex;
            log.error("Parking Lot Exception: " + parkingLotException.getErrorCode() + " - " + parkingLotException.getMessage(), parkingLotException);
            ErrorClass errorClass = new ErrorClass(parkingLotException.getErrorCode(), parkingLotException.getMessage());
            return new ResponseEntity<>(errorClass, HttpStatus.BAD_REQUEST);
        } else {
            // Handle other RuntimeExceptions
            log.error("Unexpected runtime exception: " + ex.getMessage(), ex);
            ErrorClass errorClass = new ErrorClass(ErrorCode.INTERNAL_SERVER_ERROR, ex.getMessage());
            return new ResponseEntity<>(errorClass, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(ParkingLotException.class)
    public ResponseEntity<ErrorClass> handleParkingLotException(ParkingLotException ex) {
        // Handle ParkingLotException
        String errorCode = ex.getErrorCode();
        // Check if it's an incorrect password error
        if ("INCORRECT_PASSWORD".equals(errorCode)) {
            log.error("Incorrect password: " + ex.getMessage(), ex);
            ErrorClass errorClass = new ErrorClass(errorCode, ex.getMessage());
            return new ResponseEntity<>(errorClass, HttpStatus.UNAUTHORIZED);
        }
        else if("USER_IS_ALREADY_PRESENT".equals(errorCode)){
            log.error("User already present, please sign in: " + ex.getMessage(), ex);
            ErrorClass errorClass = new ErrorClass(errorCode, ex.getMessage());
            return new ResponseEntity<>(errorClass, HttpStatus.CONFLICT);

        }else if("USER_IS_NOT_PRESENT".equals(errorCode)){
            log.error("User is not present, please sign up: " + ex.getMessage(), ex);
            ErrorClass errorClass = new ErrorClass(errorCode, ex.getMessage());
            return new ResponseEntity<>(errorClass, HttpStatus.NOT_FOUND);

        }else {
            // Handle other types of errors
            log.error("Parking Lot Exception: " + errorCode + " - " + ex.getMessage(), ex);
            ErrorClass errorClass = new ErrorClass(errorCode, ex.getMessage());
            return new ResponseEntity<>(errorClass, HttpStatus.BAD_REQUEST);
        }
    }
}
