package com.ws.spring.core._01_basics.example3;

import com.ws.spring.core._01_basics.example3.impl.HelloWorld;

public class HumanBot {

    private final HelloWorld helloWorld = new HelloWorld();

    public String greeting() {
        return "Hey body, " + helloWorld.sayHello();
    }

}
