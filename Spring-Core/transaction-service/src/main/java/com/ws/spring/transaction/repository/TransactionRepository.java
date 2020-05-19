package com.ws.spring.transaction.repository;

import com.ws.spring.transaction.domain.Transaction;

import java.util.Optional;

public interface TransactionRepository {

    Optional<Transaction> getById(Integer id);
    Transaction save(Transaction account);

}
