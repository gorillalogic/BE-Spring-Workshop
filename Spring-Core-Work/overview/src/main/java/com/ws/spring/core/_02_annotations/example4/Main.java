package com.ws.spring.core._02_annotations.example4;

import com.ws.spring.core._02_annotations.example4.impl.HelloMoon;
import com.ws.spring.core._02_annotations.example4.impl.HelloWorld;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Main {

    /*
        Example demonstrates the use of @Configuration + @Bean
     */

    private static ApplicationContext applicationContext;

    @Bean
    public HelloMoon getHelloMoon() {
        return new HelloMoon();
    }

    @Bean
    public HelloWorld getHelloWorld() {
        return new HelloWorld();
    }



    public static void main(String[] args) {
        applicationContext = new AnnotationConfigApplicationContext(Main.class);

        HelloWorld hello = applicationContext.getBean(HelloWorld.class);
        System.out.println(hello.sayHello());
    }

}
