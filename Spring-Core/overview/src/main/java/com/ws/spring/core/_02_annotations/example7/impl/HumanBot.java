package com.ws.spring.core._02_annotations.example7.impl;

import com.ws.spring.core._02_annotations.example7.Bot;
import com.ws.spring.core._02_annotations.example7.Hello;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("world")
@Component
public class HumanBot implements Bot {

    private final Hello hello;


    public HumanBot(Hello hello) {
        this.hello = hello;
    }

    @Override
    public String greeting() {
        return "Hey body, " + hello.sayHello();
    }

}
