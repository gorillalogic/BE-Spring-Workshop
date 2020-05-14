package com.ws.spring.account.repository;

import com.ws.spring.account.domain.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;

@Slf4j
@Profile("test")
@Repository
public class AccountMockRepository implements AccountRepository {

    private Map<Integer, Account> accounts;


    public AccountMockRepository() {
        this.accounts = new HashMap<>();
    }

    @Override
    public Optional<Account> getById(Integer id) {
        log.info("Repository layer looking for Account entity with id: " + id);

        return Optional.of(accounts.get(id));
    }

    @Override
    public Optional<Account> getByAccountNumber(String accountNumber) {
        log.info("Repository layer looking for Account entity with accountNumber: " + accountNumber);

        return accounts.values()
                .stream()
                .filter(account -> account.getAccountNumber().equalsIgnoreCase(accountNumber))
                .findFirst();
    }

    @Override
    public Account save(Account account) {
        log.info("Repository layer saving Account: " + account);

        if(account.getId() == null) {
            account.setId(new Random().nextInt(Integer.MAX_VALUE) + 1);
        }

        accounts.put(account.getId(), account);

        return account;
    }

}
