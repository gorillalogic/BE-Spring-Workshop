package com.ws.spring.core._01_basics.example3;

public class ReusableBot {

    private final Hello hello;


    public ReusableBot(Hello hello) {
        this.hello = hello;
    }

    public String greeting() {
        return "Hey, " + hello.sayHello();
    }

}
