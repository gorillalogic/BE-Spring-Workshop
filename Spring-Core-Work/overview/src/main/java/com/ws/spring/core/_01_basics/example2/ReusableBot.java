package com.ws.spring.core._01_basics.example2;

import com.ws.spring.core._01_basics.example2.impl.HelloMoon;

public class ReusableBot {

    private final HelloMoon helloMoon;


    public ReusableBot(HelloMoon helloMoon) {
        this.helloMoon = helloMoon;
    }

    public String greeting() {
        return "Hey, " + helloMoon.sayHello();
    }

}
