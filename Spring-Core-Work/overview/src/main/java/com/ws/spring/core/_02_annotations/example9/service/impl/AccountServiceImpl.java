package com.ws.spring.core._02_annotations.example9.service.impl;

import com.ws.spring.core._02_annotations.example9.repository.AccountRepository;
import com.ws.spring.core._02_annotations.example9.service.AccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;


    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void getAccountById(String id) {
        accountRepository.getAccountById(id);
        System.out.println("Return Account object from service!");
    }

}
