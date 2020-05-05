package com.ws.spring.core._02_annotations.example7.impl;

import com.ws.spring.core._02_annotations.example7.Hello;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile({"dev", "qa"})
@Component
public class HelloMoon2 extends HelloMoon {

    public String sayHello() {
        return "Custom message profile WORLD";
    }

}
