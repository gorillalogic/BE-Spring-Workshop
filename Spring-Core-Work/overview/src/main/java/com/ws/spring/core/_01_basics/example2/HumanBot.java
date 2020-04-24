package com.ws.spring.core._01_basics.example2;


import com.ws.spring.core._01_basics.example1.impl.HelloWorld;

public class HumanBot {

    private final HelloWorld helloWorld = new HelloWorld();     // Tight coupling

    public String greeting() {
        return "Hey body, " + helloWorld.sayHello();
    }

}
