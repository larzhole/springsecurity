package com.example.demo.security;

import com.example.demo.utilities.annotations.Loggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/security")
public class SecurityController {
    private final SecurityService securityService;

    @Autowired
    public SecurityController(SecurityService securityService) {
        this.securityService = securityService;
    }

    @PostMapping
    @Loggable(shouldLogPayload = false)
    public ResponseEntity login(@RequestBody Map<String, String> credentials) throws SecurityUnauthorizedException {
        return ResponseEntity.ok(securityService.login(credentials));
    }
}
