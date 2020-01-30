package com.example.demo.fish;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "fish not found")
public class FishNotFoundException extends Exception {
    public FishNotFoundException() {
    }

    public FishNotFoundException(String message) {
        super(message);
    }
}
