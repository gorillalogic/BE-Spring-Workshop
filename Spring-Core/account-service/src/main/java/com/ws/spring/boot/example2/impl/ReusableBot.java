package com.ws.spring.boot.example2.impl;

import com.ws.spring.boot.example2.Bot;
import com.ws.spring.boot.example2.Hello;
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
