package com.ws.spring.core._02_annotations.example6.impl;

import com.ws.spring.core._02_annotations.example6.Hello;
import org.springframework.stereotype.Component;

@Component("hola")
public class HelloWorld implements Hello {

    public String sayHello() {
        return "Hello world!";
    }

}
