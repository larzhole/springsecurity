package com.example.demo.security;

import com.auth0.jwt.exceptions.JWTDecodeException;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Log4j2
@Component
public class JwtFilter extends OncePerRequestFilter {
    private JwtProvider jwtProvider;

    public JwtFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        // Allow requests to some endpoints without JWT
        final List<String> unsecureEndpoints = Arrays.asList("/actuator", "/security");
        if (unsecureEndpoints.parallelStream().anyMatch(httpServletRequest.getRequestURI()::contains)) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        try {
            final String token = getToken(httpServletRequest);
            jwtProvider.verifyToken(token);
            SecurityContextHolder.getContext().setAuthentication(jwtProvider.getAuthenticationFromToken(token));
        } catch (JWTDecodeException e) {
            handleError(httpServletRequest, "Unable to decode JWT", e);
        }

    }

    private String getToken(HttpServletRequest httpServletRequest) {
        final String header = httpServletRequest.getHeader("Authorization");

        if (StringUtils.hasText(header)) {
            final String token = header.replace("Bearer ", "");

            if (StringUtils.hasText(token)) {
                return token;
            }
        }

        throw new IllegalArgumentException("JWT is required");
    }

    private void handleError(HttpServletRequest httpServletRequest, String message, JWTDecodeException e) {

    }
}
