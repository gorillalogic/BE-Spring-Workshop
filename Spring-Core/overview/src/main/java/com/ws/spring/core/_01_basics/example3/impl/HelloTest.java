package com.ws.spring.core._01_basics.example3.impl;

import com.ws.spring.core._01_basics.example3.Hello;

public class HelloTest implements Hello {

    @Override
    public String sayHello() {
        return "Test";
    }
}
