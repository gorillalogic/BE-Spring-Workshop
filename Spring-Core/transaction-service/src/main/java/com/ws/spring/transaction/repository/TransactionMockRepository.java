package com.ws.spring.transaction.repository;

import com.ws.spring.transaction.domain.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Profile("test")
@Repository
public class TransactionMockRepository implements TransactionRepository {

    private Map<Integer, Transaction> transactions;


    public TransactionMockRepository() {
        this.transactions = new HashMap<>();
    }

    @Override
    public Optional<Transaction> getById(Integer id) {
        log.info("Repository layer looking for Transaction entity with id: " + id);

        return Optional.of(transactions.get(id));
    }

    @Override
    public Transaction save(Transaction transaction) {
        log.info("Repository layer saving Transaction: " + transaction);

        if(transaction.getId() == null) {
            transaction.setId(new Random().nextInt(Integer.MAX_VALUE) + 1);
        }

        transactions.put(transaction.getId(), transaction);

        return transaction;
    }

}
