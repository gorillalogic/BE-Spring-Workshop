package com.ws.spring.core._02_annotations.example9.service.impl;

import com.ws.spring.core._02_annotations.example9.domain.Account;
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
    public Account getById(String id) {
        System.out.println("Service layer looking for Account entity with id: " + id);
        return accountRepository.getById(id);
    }

}
