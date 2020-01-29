package com.example.demo.hello;

import java.util.List;

public interface HelloService {

    List<Hello> getHellos();

    Hello getById(Long id) throws HelloNotFoundException;

    Hello addHello(Hello hello);

    void updateHello(Hello hello, Long id) throws HelloNotFoundException;

    void deleteHello(Long id) throws HelloNotFoundException;

}
