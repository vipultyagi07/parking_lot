package com.vip.exception;

import com.vip.common.errorResponse.ErrorClass;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import java.sql.SQLException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ParkingLotException.class)
    public ResponseEntity<ErrorClass> handleParkingLotException(ParkingLotException ex) {
        String errorCode = ex.getErrorCode();
        ErrorClass errorClass;

        switch (errorCode) {
            case "INCORRECT_PASSWORD":
                log.error("Incorrect password: " + ex.getMessage(), ex);
                errorClass = new ErrorClass(errorCode, ex.getMessage());
                return new ResponseEntity<>(errorClass, HttpStatus.UNAUTHORIZED);
            case "USER_IS_ALREADY_PRESENT":
                log.error("User already present, please sign in: " + ex.getMessage(), ex);
                errorClass = new ErrorClass(errorCode, ex.getMessage());
                return new ResponseEntity<>(errorClass, HttpStatus.CONFLICT);
            case "USER_IS_NOT_PRESENT":
                log.error("User is not present, please sign up: " + ex.getMessage(), ex);
                errorClass = new ErrorClass(errorCode, ex.getMessage());
                return new ResponseEntity<>(errorClass, HttpStatus.NOT_FOUND);
            case "VEHICLE_ALREADY_PARKED":
                log.error("Vehicle already parked: " + ex.getMessage(), ex);
                errorClass = new ErrorClass(errorCode, ex.getMessage());
                return new ResponseEntity<>(errorClass, HttpStatus.CONFLICT);
            default:
                log.error("Parking Lot Exception: " + errorCode + " - " + ex.getMessage(), ex);
                errorClass = new ErrorClass(errorCode, ex.getMessage());
                return new ResponseEntity<>(errorClass, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorClass> handleValidationException(MethodArgumentNotValidException ex) {
        log.error("Validation error: " + ex.getMessage(), ex);
        ErrorClass errorClass = new ErrorClass("VALIDATION_ERROR", ex.getBindingResult().toString());
        return new ResponseEntity<>(errorClass, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorClass> handleNoHandlerFoundException(NoHandlerFoundException ex, WebRequest request) {
        log.error("No handler found: " + ex.getRequestURL(), ex);
        ErrorClass errorClass = new ErrorClass("NOT_FOUND", "No handler found for " + ex.getRequestURL());
        return new ResponseEntity<>(errorClass, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorClass> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        log.error("Method argument type mismatch: " + ex.getName(), ex);
        ErrorClass errorClass = new ErrorClass("ARGUMENT_TYPE_MISMATCH", "Argument type mismatch for " + ex.getName());
        return new ResponseEntity<>(errorClass, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorClass> handleConstraintViolationException(ConstraintViolationException ex) {
        log.error("Constraint violation: " + ex.getMessage(), ex);
        ErrorClass errorClass = new ErrorClass("CONSTRAINT_VIOLATION", ex.getMessage());
        return new ResponseEntity<>(errorClass, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ErrorClass> handleSQLException(SQLException ex) {
        log.error("SQL error: " + ex.getMessage(), ex);
        ErrorClass errorClass = new ErrorClass("SQL_ERROR", ex.getMessage());
        return new ResponseEntity<>(errorClass, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorClass> handleGenericException(Exception ex) {
        log.error("An error occurred: " + ex.getMessage(), ex);
        ErrorClass errorClass = new ErrorClass("INTERNAL_SERVER_ERROR", ex.getMessage());
        return new ResponseEntity<>(errorClass, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
