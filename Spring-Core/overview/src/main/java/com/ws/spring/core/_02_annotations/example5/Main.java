package com.ws.spring.core._02_annotations.example5;

import com.ws.spring.core._02_annotations.example5.impl.HelloMoon;
import com.ws.spring.core._02_annotations.example5.impl.HelloWorld;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;

@Import(BeanConfig.class)
public class Main {

    /*
        Example demonstrates the use of @Import + @ComponentScan
     */

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        Hello hello;
        applicationContext = new AnnotationConfigApplicationContext(Main.class);

        hello = applicationContext.getBean(HelloWorld.class);
        System.out.println(hello.sayHello());

        hello = applicationContext.getBean(HelloMoon.class);
        System.out.println(hello.sayHello());
    }

}
