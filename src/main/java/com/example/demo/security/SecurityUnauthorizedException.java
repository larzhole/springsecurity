package com.example.demo.security;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Unauthorized")
public class SecurityUnauthorizedException extends Exception {
    public SecurityUnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
