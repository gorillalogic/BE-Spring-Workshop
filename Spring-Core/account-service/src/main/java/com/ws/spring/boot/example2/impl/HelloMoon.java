package com.ws.spring.boot.example2.impl;

import com.ws.spring.boot.example2.Hello;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("moon")
@Component
public class HelloMoon implements Hello {

//    @Value("${example2.property.hello.moon}")
//    private String helloMessage;


//    @Value("${example2.property.hello.moonX:Default Hello Message!}")     // Property with default values
//    private String helloMessage;


//    @Value("Hello message with ")       // Property as literal definition
//    private String helloMessage;
//
//    @Value("10")                        // Property as literal definition
//    private int number;


//    @Value("${HOME}")       // Environment properties
//    private String helloMessage;


//    @Value("#{systemProperties['java.home']}")       // SpEL property resolution
//    private String helloMessage;

    @Value("#{'${example2.property.list}'.split(',')}")       // SpEL property resolution
    private List<String> helloMessage;

    public String sayHello() {
//        return helloMessage;

//        return helloMessage + number;     // Property as literal definition

        return helloMessage.toString();   // SpEL property resolution
    }

}
