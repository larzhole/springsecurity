package com.example.demo.security;

import java.util.Map;

public interface SecurityService {

    JwtResponse login(Map<String, String> credentials) throws SecurityUnauthorizedException;

}
