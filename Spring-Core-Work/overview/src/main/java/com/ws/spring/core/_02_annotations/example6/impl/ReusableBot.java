package com.ws.spring.core._02_annotations.example6.impl;

import com.ws.spring.core._02_annotations.example6.Bot;
import com.ws.spring.core._02_annotations.example6.Hello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReusableBot implements Bot {

    private final Hello hello;

    @Autowired  // This is optional since latest versions of Spring
    public ReusableBot(Hello helloMoon) {
        this.hello = helloMoon;
    }

    @Override
    public String greeting() {
        return "Hey, " + hello.sayHello();
    }

}
