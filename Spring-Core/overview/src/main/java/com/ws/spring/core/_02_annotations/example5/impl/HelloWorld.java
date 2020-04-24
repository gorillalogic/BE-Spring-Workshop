package com.ws.spring.core._02_annotations.example5.impl;

import com.ws.spring.core._02_annotations.example5.Hello;
import org.springframework.stereotype.Component;

@Component
public class HelloWorld implements Hello {

    public String sayHello() {
        return "Hello world!";
    }

}
