package com.ws.spring.core._02_annotations.example9;

import com.ws.spring.core._02_annotations.example9.controller.AccountController;
import com.ws.spring.core._02_annotations.example9.repository.AccountRepository;
import com.ws.spring.core._02_annotations.example9.service.AccountService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Import;

@Import(BeanConfig.class)
public class Main {

    /*
        Example demonstrates the use of @Repository, @Service and @Controller
     */

    private static ApplicationContext applicationContext;

    public static void main(String[] args) {
        System.setProperty("spring.profiles.active", "test");
        applicationContext = new AnnotationConfigApplicationContext(Main.class);

//        AccountRepository accountRepository = applicationContext.getBean(AccountRepository.class);
//        accountRepository.getAccountById("anAccountId");
//
//        AccountService accountService = applicationContext.getBean(AccountService.class);
//        accountService.getAccountById("anAccountId");

        AccountController accountController = applicationContext.getBean(AccountController.class);
        accountController.getAccountById("anAccountId");
    }

}
