package com.ws.spring.core._02_annotations.example8.impl;

import com.ws.spring.core._02_annotations.example8.Hello;
import com.ws.spring.core._02_annotations.example8.Bot;
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
