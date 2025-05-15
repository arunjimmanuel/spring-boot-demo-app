package com.arun.immanuel.jobtracker.configuration;

import com.arun.immanuel.jobtracker.utils.AppConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class CustomAuthEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException {

        int status = HttpServletResponse.SC_FORBIDDEN;
        String error = "Unauthorized";
        String message = authException.getMessage();
        if (authException instanceof InsufficientAuthenticationException) {
            status = HttpServletResponse.SC_FORBIDDEN;
            error = "Forbidden";
            message = "Access denied. Authentication is required.";
        } else if (authException instanceof BadCredentialsException) {
            status = HttpServletResponse.SC_UNAUTHORIZED;
            message = "Invalid username or password";
        } else if (authException instanceof InternalAuthenticationServiceException) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
            error = "Authentication Service Error";
            message = "Internal authentication service error";
        }

        if (!response.isCommitted()) {
            response.setStatus(status);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put(AppConstants.ResponseKey.TIMESTAMP, System.currentTimeMillis());
            responseBody.put(AppConstants.ResponseKey.STATUS, status);
            responseBody.put(AppConstants.ResponseKey.ERROR, error);
            responseBody.put(AppConstants.ResponseKey.MESSAGE, message);
            responseBody.put(AppConstants.ResponseKey.PATH, request.getRequestURI());

            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), responseBody);
            log.error("Auth error: {} - {}", authException.getClass().getSimpleName(), authException.getMessage());
        }
    }
}