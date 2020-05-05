package com.ws.spring.core._02_annotations.example8;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;

@Import(BeanConfig.class)
public class Main {

    /*
        Example demonstrates high/low coupling with profiles
     */

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "world");
        applicationContext = new AnnotationConfigApplicationContext(Main.class);

        Bot bot = applicationContext.getBean(Bot.class);
        System.out.println(bot.greeting());
    }

}
