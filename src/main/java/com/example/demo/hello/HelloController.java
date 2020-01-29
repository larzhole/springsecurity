package com.example.demo.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/hello")
public class HelloController {
    private final HelloService helloService;

    @Autowired
    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping
    public ResponseEntity<List<Hello>> getHellos() {
        return ResponseEntity.ok(helloService.getHellos());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity<Hello> getHelloById(@PathVariable @NotNull Long id) throws HelloNotFoundException {
        return ResponseEntity.ok(helloService.getById(id));
    }
}
