package com.ws.spring.core._02_annotations.example9.controller;

import com.ws.spring.core._02_annotations.example9.domain.Account;
import com.ws.spring.core._02_annotations.example9.service.AccountService;
import org.springframework.stereotype.Controller;

@Controller
public class AccountController {

    private final AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    public Account getById(String id) {
        System.out.println("Controller layer looking for Account entity with id: " + id);
        return accountService.getById(id);
    }

}
