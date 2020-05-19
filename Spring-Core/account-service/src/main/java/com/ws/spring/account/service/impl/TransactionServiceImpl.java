package com.ws.spring.account.service.impl;

import com.ws.spring.account.client.TransactionClient;
import com.ws.spring.account.dto.TransactionDto;
import com.ws.spring.account.dto.request.TransactionCreateRequestDto;
import com.ws.spring.account.exception.TransactionException;
import com.ws.spring.account.service.TransactionService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    // We will integrate this with Transaction Service using any REST Template solution
    private final TransactionClient transactionClient;
    private final String serviceUrl;


    public TransactionServiceImpl(TransactionClient transactionClient, @Value("${transaction-service.url}") String serviceUrl) {
        this.transactionClient = transactionClient;
        this.serviceUrl = serviceUrl;
    }

    @Override
    public Optional<TransactionDto> registerTransaction(TransactionCreateRequestDto request) throws TransactionException {
        log.info("Service layer creating Transaction with request {}", request);

//        TransactionDto newTransaction = new TransactionDto();
//        newTransaction.setId(new Random().nextInt(Integer.MAX_VALUE) + 1);
//        newTransaction.setAccountNumberFrom(request.getAccountNumberFrom());
//        newTransaction.setAccountNumberTo(request.getAccountNumberTo());
//        newTransaction.setType(request.getType());
//        newTransaction.setAmount(request.getAmount());
//        newTransaction.setDateTime(LocalDateTime.now());

        TransactionDto transactionDto = null;

        try {

            transactionDto = transactionClient.create(request);

        } catch (FeignException ex) {
            throw new TransactionException("Transaction service not responding.");
        }

        return Optional.of(transactionDto);
    }

    @Override
    public Optional<TransactionDto> registerTransactionRestTemplate(TransactionCreateRequestDto request) throws TransactionException {
        log.info("Service layer creating Transaction with request {}", request);

        HttpEntity<TransactionCreateRequestDto> requestBody = new HttpEntity<>(request);
        ResponseEntity<TransactionDto> response = new RestTemplate().postForEntity(serviceUrl + "/", requestBody, TransactionDto.class);

        return Optional.of(response.getBody());
    }

}
