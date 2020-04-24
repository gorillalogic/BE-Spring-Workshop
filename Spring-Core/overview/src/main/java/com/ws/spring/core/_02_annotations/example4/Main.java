package com.ws.spring.core._02_annotations.example4;

import com.ws.spring.core._02_annotations.example4.impl.HelloMoon;
import com.ws.spring.core._02_annotations.example4.impl.HelloWorld;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

@Configuration
public class Main {

    private static ApplicationContext applicationContext;

    @Bean
//    @Lazy
//    @Scope("prototype")
    public HelloMoon getHelloMoon() {
        return new HelloMoon();
    }


    public static void main(String[] args) {
        applicationContext = new AnnotationConfigApplicationContext(Main.class);

        HelloMoon helloMoon = applicationContext.getBean(HelloMoon.class);
//        System.out.println(helloMoon.sayHello());
        System.out.println(helloMoon);

        HelloMoon helloMoon2 = applicationContext.getBean(HelloMoon.class);
        System.out.println(helloMoon2);


    }









//    /*
//        Example demonstrates the use of @Configuration + @Bean
//     */
//
//    private static ApplicationContext applicationContext;
//
//    @Bean
//    public HelloMoon getHelloMoon() {
//        return new HelloMoon();
//    }
//
//    @Bean
////    @Scope("prototype")
//    public HelloWorld getHelloWorld() {
//        return new HelloWorld();
//    }
//
//
//
//    public static void main(String[] args) {
//        applicationContext = new AnnotationConfigApplicationContext(Main.class);
//
//        HelloWorld hello = applicationContext.getBean(HelloWorld.class);
//        System.out.println(hello.sayHello());
//
////        System.out.println(hello);
////        hello = applicationContext.getBean(HelloWorld.class);
////        System.out.println(hello);
//    }

}
