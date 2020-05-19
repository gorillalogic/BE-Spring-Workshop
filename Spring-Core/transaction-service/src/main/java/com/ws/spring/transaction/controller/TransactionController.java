package com.ws.spring.transaction.controller;

import com.ws.spring.transaction.domain.Transaction;
import com.ws.spring.transaction.dto.TransactionDto;
import com.ws.spring.transaction.dto.request.TransactionCreateRequestDto;
import com.ws.spring.transaction.exception.ResourceNotFoundException;
import com.ws.spring.transaction.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Slf4j
@Controller
public class TransactionController {

    private final TransactionService transactionService;


    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Integer id) throws Exception {
        log.info("Controller layer looking for Transaction entity with id {}", id);

        Transaction transaction = transactionService.getById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));

        // We are doing this very dummy, but can be done easily with some other libraries
        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setAccountNumberFrom(transaction.getAccountNumberFrom());
        dto.setAccountNumberTo(transaction.getAccountNumberTo());
        dto.setType(transaction.getType());
        dto.setAmount(transaction.getAmount());
        dto.setDateTime(transaction.getDateTime());

        return new ResponseEntity(transaction, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody TransactionCreateRequestDto request) {
        log.info("Controller layer creating transaction with request {}", request);

        Transaction transaction = transactionService.create(request);

        // We are doing this very dummy, but can be done easily with some other libraries
        TransactionDto dto = new TransactionDto();
        dto.setId(transaction.getId());
        dto.setAccountNumberFrom(transaction.getAccountNumberFrom());
        dto.setAccountNumberTo(transaction.getAccountNumberTo());
        dto.setType(transaction.getType());
        dto.setAmount(transaction.getAmount());
        dto.setDateTime(transaction.getDateTime());

        return new ResponseEntity(dto, HttpStatus.CREATED);
    }

}
