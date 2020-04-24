package com.ws.spring.core._02_annotations.example6;

import com.ws.spring.core._02_annotations.example6.impl.HumanBot;
import com.ws.spring.core._02_annotations.example6.impl.ReusableBot;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;

@Import(BeanConfig.class)
public class Main {

    /*
        Example demonstrates the use of @Autowired + @Qualifier + Type of injections (Field, Constructor and Setter)
     */

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        Bot bot;
        applicationContext = new AnnotationConfigApplicationContext(Main.class);

        bot = applicationContext.getBean(HumanBot.class);
        System.out.println(bot.greeting());

//        bot = applicationContext.getBean(ReusableBot.class);
//        System.out.println(bot.greeting());
    }

}
