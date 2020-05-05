package com.ws.spring.core._02_annotations.example7.impl;

import com.ws.spring.core._02_annotations.example7.Hello;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("moon")
@Component
public class HelloMoon implements Hello {

    public String sayHello() {
        return "Hello moon!";
    }

}