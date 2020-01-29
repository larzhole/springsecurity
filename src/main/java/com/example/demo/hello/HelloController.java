package com.example.demo.hello;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@CrossOrigin
@RestController
@RequestMapping(path = "/api/v1/hello")
public class HelloController {
    private final HelloService helloService;
    private final ModelMapper modelMapper;

    @Autowired
    public HelloController(HelloService helloService,
                           ModelMapper modelMapper) {
        this.helloService = helloService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public ResponseEntity getHellos() {
        return ResponseEntity.ok(helloService.getHellos());
    }

    @GetMapping(path = "{id}")
    public ResponseEntity getHelloById(@PathVariable @NotNull Long id) throws HelloNotFoundException {
        return ResponseEntity.ok(helloService.getById(id));
    }

    @PostMapping
    public ResponseEntity addHello(HelloDTO helloDTO) {
        return ResponseEntity.ok(helloService.addHello(dtoToEntity(helloDTO)));
    }

    @PutMapping(path = "{id}")
    public ResponseEntity updateHello(@PathVariable Long id,
                                      @RequestBody HelloDTO helloDTO) throws HelloNotFoundException {
        helloService.updateHello(id, dtoToEntity(helloDTO));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(path = "{id}")
    public ResponseEntity deleteHello(@PathVariable Long id) throws HelloNotFoundException {
        helloService.deleteHello(id);
        return ResponseEntity.noContent().build();
    }

    private Hello dtoToEntity(HelloDTO helloDTO) {
        return modelMapper.map(helloDTO, Hello.class);
    }
}
