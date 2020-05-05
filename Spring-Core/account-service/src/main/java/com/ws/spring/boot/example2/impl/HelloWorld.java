package com.ws.spring.boot.example2.impl;

import com.ws.spring.boot.example2.Hello;
import com.ws.spring.boot.example2.properties.HelloProperties;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("world")
@Component
public class HelloWorld implements Hello {

    private final HelloProperties helloProperties;


    public HelloWorld(HelloProperties helloProperties) {
        this.helloProperties = helloProperties;
    }

    public String sayHello() {
        return helloProperties.getWorldX();
    }

}
