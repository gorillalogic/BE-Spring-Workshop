package com.ws.spring.account.service;

import com.ws.spring.account.dto.TransactionDto;
import com.ws.spring.account.dto.request.TransactionCreateRequestDto;
import com.ws.spring.account.exception.TransactionException;

import java.util.Optional;

public interface TransactionService {

    Optional<TransactionDto> registerTransaction(TransactionCreateRequestDto request) throws TransactionException;
    Optional<TransactionDto> registerTransactionRestTemplate(TransactionCreateRequestDto request) throws TransactionException;

}
