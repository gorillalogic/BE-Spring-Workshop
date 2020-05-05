package com.ws.spring.boot.example2;

import com.ws.spring.boot.example2.properties.HelloProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;

@EnableConfigurationProperties({HelloProperties.class})
@SpringBootApplication
public class Main {

	/*
        Example demonstrates the use of @Value + @ConfigurationProperty
     */

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(Main.class, args);

		Bot bot = applicationContext.getBean(Bot.class);
		System.out.println(bot.greeting());
	}

}
