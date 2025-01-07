package com.vip.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.async.AsyncRequestTimeoutException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(ParkingLotException.class)
    public ResponseEntity<ErrorClass> handlePmsException(ParkingLotException ex) {
        // Handle Exception
        String errorCode = ex.getErrorCode();
        HttpStatus httpStatus = ex.getHttpStatus();
        // Check if it's an incorrect password error
        if ("INCORRECT_PASSWORD".equals(errorCode)) {
            log.error("Incorrect password: " + ex.getMessage(), ex);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String stackTrace = sw.toString();
            ErrorClass errorClass = new ErrorClass(httpStatus, errorCode, ex.getMessage(),stackTrace);
            return new ResponseEntity<>(errorClass, HttpStatus.UNAUTHORIZED);
        } else if ("USER_IS_ALREADY_PRESENT".equals(errorCode)) {
            log.error("User already present, please sign in: " + ex.getMessage(), ex);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String stackTrace = sw.toString();
            ErrorClass errorClass = new ErrorClass(httpStatus, errorCode, ex.getMessage(),stackTrace);
            return new ResponseEntity<>(errorClass, HttpStatus.CONFLICT);

        } else if ("USER_IS_NOT_PRESENT".equals(errorCode)) {
            log.error("User is not present, please sign up: " + ex.getMessage(), ex);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            ex.printStackTrace(pw);
            String stackTrace = sw.toString();
            ErrorClass errorClass = new ErrorClass(httpStatus, errorCode, ex.getMessage(),stackTrace);
            return new ResponseEntity<>(errorClass, HttpStatus.NOT_FOUND);

        } else {
            // Handle all other types of errors
//            ErrorClass errorClass = new ErrorClass(httpStatus, errorCode, ex.getMessage());
            ErrorClass errorClass = new ErrorClass(httpStatus, errorCode, ex.getMessage(),ex.getErrorStackTrace());
            if (Objects.nonNull(httpStatus)) {
                log.error("handleException: error associated with error message: {}", ex.getMessage());
                return new ResponseEntity<>(errorClass, httpStatus);
            }
            return new ResponseEntity<>(errorClass, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorClass> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        log.error("Invalid JSON format: " + ex.getMessage(), ex);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String stackTrace = sw.toString();
        ErrorClass errorClass = new ErrorClass(HttpStatus.BAD_REQUEST, "INVALID_JSON", ex.getMessage(),stackTrace);
        return new ResponseEntity<>(errorClass, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorClass> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        log.error("Request method not supported: " + ex.getMethod(), ex);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String stackTrace = sw.toString();
        ErrorClass errorClass = new ErrorClass(HttpStatus.METHOD_NOT_ALLOWED, "METHOD_NOT_ALLOWED", ex.getMessage(),stackTrace);
        return new ResponseEntity<>(errorClass, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorClass> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Validation failed: " + ex.getMessage(), ex);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String stackTrace = sw.toString();
        ErrorClass errorClass = new ErrorClass(HttpStatus.BAD_REQUEST, "VALIDATION_FAILED", ex.getMessage(),stackTrace);
        return new ResponseEntity<>(errorClass, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorClass> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        log.error("Missing request parameter: " + ex.getParameterName(), ex);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String stackTrace = sw.toString();
        ErrorClass errorClass = new ErrorClass(HttpStatus.BAD_REQUEST, "MISSING_PARAMETER", "Missing request parameter: " + ex.getParameterName(),stackTrace);
        return new ResponseEntity<>(errorClass, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ServletRequestBindingException.class)
    public ResponseEntity<ErrorClass> handleServletRequestBindingException(ServletRequestBindingException ex) {
        log.error("Request binding error: " + ex.getMessage(), ex);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String stackTrace = sw.toString();
        ErrorClass errorClass = new ErrorClass(HttpStatus.BAD_REQUEST, "REQUEST_BINDING_ERROR", ex.getMessage(),stackTrace);
        return new ResponseEntity<>(errorClass, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorClass> handleNoHandlerFoundException(NoHandlerFoundException ex) {
        log.error("No handler found for request: " + ex.getHttpMethod() + " " + ex.getRequestURL(), ex);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String stackTrace = sw.toString();
        ErrorClass errorClass = new ErrorClass(HttpStatus.NOT_FOUND, "NO_HANDLER_FOUND", ex.getMessage(),stackTrace);
        return new ResponseEntity<>(errorClass, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AsyncRequestTimeoutException.class)
    public ResponseEntity<ErrorClass> handleAsyncRequestTimeoutException(AsyncRequestTimeoutException ex) {
        log.error("Async request timeout: " + ex.getMessage(), ex);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String stackTrace = sw.toString();
        ErrorClass errorClass = new ErrorClass(HttpStatus.SERVICE_UNAVAILABLE, "ASYNC_TIMEOUT", ex.getMessage(),stackTrace);
        return new ResponseEntity<>(errorClass, HttpStatus.SERVICE_UNAVAILABLE);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorClass> handleAccessDeniedException(AccessDeniedException ex) {
        log.error("Access denied: " + ex.getMessage(), ex);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String stackTrace = sw.toString();
        ErrorClass errorClass = new ErrorClass(HttpStatus.FORBIDDEN, "ACCESS_DENIED", ex.getMessage(),stackTrace);
        return new ResponseEntity<>(errorClass, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorClass> handleGeneralException(Exception ex) {
        log.error("An unexpected error occurred: " + ex.getMessage(), ex);
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        String stackTrace = sw.toString();
        ErrorClass errorClass = new ErrorClass(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", ex.getMessage(),stackTrace);
        return new ResponseEntity<>(errorClass, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
