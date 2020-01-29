package com.example.demo.hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HelloServiceDefault implements HelloService {
    private final HelloRepository helloRepository;

    @Autowired
    public HelloServiceDefault(HelloRepository helloRepository) {
        this.helloRepository = helloRepository;
    }

    @Override
    public List<Hello> getHellos() {
        return helloRepository.findAll();
    }

    @Override
    public Hello getById(Long id) throws HelloNotFoundException {
        return helloRepository.findById(id).orElseThrow(() -> new HelloNotFoundException());
    }

    @Override
    public Hello addHello(Hello hello) {
        return helloRepository.save(hello);
    }

    @Override
    public void updateHello(Long id, Hello hello) throws HelloNotFoundException {
        checkIfExistsById(id);
        hello.setHelloId(id);
        helloRepository.save(hello);
    }

    @Override
    public void deleteHello(Long id) throws HelloNotFoundException {
        checkIfExistsById(id);
        helloRepository.deleteById(id);
    }

    void checkIfExistsById(Long id) throws HelloNotFoundException {
        if (!helloRepository.existsById(id)) {
            throw new HelloNotFoundException("Hello not found by id: " + id);
        }
    }

}
