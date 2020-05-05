package com.ws.spring.core._02_annotations.example9.repository;

import com.ws.spring.core._02_annotations.example9.domain.Account;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Random;

@Profile("test")
@Repository
public class AccountMockRepository implements AccountRepository {

    @Override
    public Account getById(String id) {
        System.out.println("Repository layer looking for Account entity with id: " + id);

        Random random = new Random();

        Account account = new Account();
        account.setId(String.valueOf(random.nextInt()));
        account.setAccountNumber(String.valueOf(random.nextInt()));
        account.setBalance(0);

        return account;
    }

}
