package com.ws.spring.boot.example1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Main {

	/*
        Example demonstrates the use of @SpringBootApplication + Profiles
     */

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(Main.class, args);

		Bot bot = applicationContext.getBean(Bot.class);
		System.out.println(bot.greeting());
	}

}
