package com.ws.spring.transaction.service.impl;

import com.ws.spring.transaction.domain.Transaction;
import com.ws.spring.transaction.dto.request.TransactionCreateRequestDto;
import com.ws.spring.transaction.repository.TransactionRepository;
import com.ws.spring.transaction.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;


    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Optional<Transaction> getById(Integer id) {
        log.info("Service layer looking for Transaction entity with id {}", id);

        return transactionRepository.getById(id);
    }

    @Override
    public Transaction create(TransactionCreateRequestDto request) {
        log.info("Service layer creating Transaction with request {}", request);

        Transaction newTransaction = new Transaction();
        newTransaction.setId(new Random().nextInt(Integer.MAX_VALUE) + 1);
        newTransaction.setAccountNumberFrom(request.getAccountNumberFrom());
        newTransaction.setAccountNumberTo(request.getAccountNumberTo());
        newTransaction.setType(request.getType());
        newTransaction.setAmount(request.getAmount());
        newTransaction.setDateTime(LocalDateTime.now());

        return transactionRepository.save(newTransaction);
    }

}
