package com.ws.spring.core._02_annotations.example7;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;

@Import(BeanConfig.class)
public class Main {

    /*
        Example demonstrates the use and behavior of profiles
     */

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "moon");
        applicationContext = new AnnotationConfigApplicationContext(Main.class);

        Bot bot = applicationContext.getBean(Bot.class);
        System.out.println(bot.greeting());
    }

}
