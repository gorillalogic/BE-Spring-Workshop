package com.ws.spring.account.service;

import com.ws.spring.account.dto.TransactionDto;
import com.ws.spring.account.dto.request.TransactionCreateRequestDto;

import java.util.Optional;

public interface TransactionService {

    Optional<TransactionDto> registerTransaction(TransactionCreateRequestDto request);

}
