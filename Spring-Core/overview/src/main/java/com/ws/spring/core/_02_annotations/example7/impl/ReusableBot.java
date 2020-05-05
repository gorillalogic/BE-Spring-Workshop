package com.ws.spring.core._02_annotations.example7.impl;

import com.ws.spring.core._02_annotations.example7.Hello;
import com.ws.spring.core._02_annotations.example7.Bot;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("moon")
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
