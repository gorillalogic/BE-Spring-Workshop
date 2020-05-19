package com.ws.spring.transaction.service;

import com.ws.spring.transaction.domain.Transaction;
import com.ws.spring.transaction.dto.request.TransactionCreateRequestDto;
import com.ws.spring.transaction.exception.ResourceNotFoundException;

import java.util.Optional;

public interface TransactionService {

    Optional<Transaction> getById(Integer id) throws ResourceNotFoundException;
    Transaction create(TransactionCreateRequestDto request);

}
