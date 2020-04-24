package com.ws.spring.core._02_annotations.example9.controller;

import com.ws.spring.core._02_annotations.example9.service.AccountService;
import org.springframework.stereotype.Controller;

@Controller
public class AccountController {

    private final AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    public void getAccountById(String id) {
        accountService.getAccountById(id);
        System.out.println("Return Account object from controller!");
    }

}
