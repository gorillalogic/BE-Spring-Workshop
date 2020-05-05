package com.ws.spring.boot.example1.impl;

import com.ws.spring.boot.example1.Bot;
import com.ws.spring.boot.example1.Hello;
import org.springframework.stereotype.Component;

@Component
public class ReusableBot implements Bot {

    private final Hello hello;


    public ReusableBot(Hello hello) {
        this.hello = hello;
    }

    @Override
    public String greeting() {
        return "Hey, " + hello.sayHello();
    }

}
