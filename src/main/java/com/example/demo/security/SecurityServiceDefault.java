package com.example.demo.security;

import com.auth0.jwt.exceptions.JWTCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SecurityServiceDefault implements SecurityService {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @Autowired
    public SecurityServiceDefault(AuthenticationManager authenticationManager, JwtProvider jwtProvider) {
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
    }

    @Override
    public JwtResponse login(Map<String, String> credentials) throws SecurityUnauthorizedException {
        try {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(credentials.get("username"), credentials.get("password"))
            );
            final String token = jwtProvider.createToken(authentication, credentials.get("username"));

            return new JwtResponse(token);
        } catch (IllegalArgumentException | AuthenticationException e) {
            throw new SecurityUnauthorizedException("Unable to login", e);
        } catch (JWTCreationException e) {
            throw new SecurityUnauthorizedException("Unable to create token", e);
        }
    }
}
