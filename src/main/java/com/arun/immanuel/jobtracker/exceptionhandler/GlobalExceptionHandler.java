package com.arun.immanuel.jobtracker.exceptionhandler;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.arun.immanuel.jobtracker.exception.InvalidCredentialsException;
import com.arun.immanuel.jobtracker.exception.UserAlreadyExistsException;
import com.arun.immanuel.jobtracker.utils.AppConstants;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Value("${spring.profiles.active:}")
    private String activeProfile;

    private boolean isDev() {
        return "dev".equalsIgnoreCase(activeProfile);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleUserAlreadyExists(UserAlreadyExistsException ex,
            HttpServletRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(AppConstants.ResponseKey.TIMESTAMP, new Date());
        body.put(AppConstants.ResponseKey.STATUS, HttpStatus.BAD_REQUEST.value());
        body.put(AppConstants.ResponseKey.ERROR, "Bad Request");
        body.put(AppConstants.ResponseKey.MESSAGE, ex.getMessage());
        body.put(AppConstants.ResponseKey.PATH, request.getRequestURI());

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidCredentials(
            InvalidCredentialsException ex,
            HttpServletRequest request) {

        Map<String, Object> body = new LinkedHashMap<>();
        body.put(AppConstants.ResponseKey.TIMESTAMP, new Date());
        body.put(AppConstants.ResponseKey.STATUS, HttpStatus.UNAUTHORIZED.value());
        body.put(AppConstants.ResponseKey.ERROR, "Unauthorized");
        body.put(AppConstants.ResponseKey.MESSAGE, ex.getMessage());
        body.put(AppConstants.ResponseKey.PATH, request.getRequestURI());

        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleBadRequest(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return buildError(400, "Bad Request", ex.getMessage(), request);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handle404(NoHandlerFoundException ex, HttpServletRequest request) {
        return buildError(404, "Not Found", "No handler found for the requested path", request);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handle405(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        return buildError(405, "Method Not Allowed", "HTTP method not allowed on this endpoint", request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex, HttpServletRequest request) {
        log.error("Internal Server Error: ", ex);
        String message = isDev() ? ex.getMessage() : "Something went wrong. Please contact support.";
        return buildError(500, "Internal Server Error", message, request);
    }

    private ResponseEntity<Object> buildError(int status, String error, String message, HttpServletRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put(AppConstants.ResponseKey.TIMESTAMP, System.currentTimeMillis());
        body.put(AppConstants.ResponseKey.STATUS, status);
        body.put(AppConstants.ResponseKey.ERROR, error);
        body.put(AppConstants.ResponseKey.MESSAGE, message);
        body.put(AppConstants.ResponseKey.PATH, request.getRequestURI());

        return ResponseEntity.status(status).body(body);
    }
}