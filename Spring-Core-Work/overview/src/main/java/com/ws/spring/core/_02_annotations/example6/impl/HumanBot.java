package com.ws.spring.core._02_annotations.example6.impl;

import com.ws.spring.core._02_annotations.example6.Bot;
import com.ws.spring.core._02_annotations.example6.Hello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class HumanBot implements Bot {

    @Autowired
    @Qualifier("helloWorld")
    private Hello hello;        // This is known as field injection


//    public HumanBot(@Qualifier("helloWorld") Hello hello) {
//        this.hello = hello;         // This is known as constructor injection
//    }
//
//    @Autowired
//    @Qualifier("helloWorld")
//    public void setHello(Hello hello) {
//        this.hello = hello;     // This is known as setter injection
//    }

    @Override
    public String greeting() {
        return "Hey body, " + hello.sayHello();
    }

}
