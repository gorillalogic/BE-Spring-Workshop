package com.ws.spring.boot.example1.impl;

import com.ws.spring.boot.example1.Hello;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("world")
@Component
public class HelloWorld implements Hello {

    public String sayHello() {
        return "Hello world!";
    }

}
