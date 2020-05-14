package com.ws.spring.account.service.impl;

import com.ws.spring.account.dto.TransactionDto;
import com.ws.spring.account.dto.request.TransactionCreateRequestDto;
import com.ws.spring.account.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    // We will integrate this with Transaction Service using any REST Template solution

    @Override
    public Optional<TransactionDto> registerTransaction(TransactionCreateRequestDto request) {
        log.info("Service layer creating Transaction with request {}", request);

        TransactionDto newTransaction = new TransactionDto();
        newTransaction.setId(new Random().nextInt(Integer.MAX_VALUE) + 1);
        newTransaction.setAccountNumberFrom(request.getAccountNumberFrom());
        newTransaction.setAccountNumberTo(request.getAccountNumberTo());
        newTransaction.setType(request.getType());
        newTransaction.setAmount(request.getAmount());
        newTransaction.setDateTime(LocalDateTime.now());

        return Optional.of(newTransaction);
    }

}
