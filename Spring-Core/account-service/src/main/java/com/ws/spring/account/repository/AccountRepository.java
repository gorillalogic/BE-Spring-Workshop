package com.ws.spring.account.repository;

import com.ws.spring.account.domain.Account;

import java.util.Optional;

public interface AccountRepository {

    Optional<Account> getById(String id);
    Optional<Account> getByAccountNumber(String accountNumber);
    Account save(Account account);

}
