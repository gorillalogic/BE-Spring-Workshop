package com.ws.spring.core._02_annotations.example9.repository;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Profile("test")
@Repository
public class AccountMockRepository implements AccountRepository {

    @Override
    public void getAccountById(String id) {
        System.out.println("Return Account object from repository!");
    }

}
