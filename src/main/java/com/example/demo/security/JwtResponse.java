package com.example.demo.security;

import lombok.Getter;

@Getter
public class JwtResponse {
    private final String token;

    JwtResponse(String token) {
        this.token = token;
    }
}
