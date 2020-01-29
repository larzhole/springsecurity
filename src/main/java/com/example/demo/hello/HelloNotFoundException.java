package com.example.demo.hello;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "hello not found")
public class HelloNotFoundException extends Exception {

    public HelloNotFoundException() {
    }

    public HelloNotFoundException(String message) {
        super(message);
    }
}
