package com.ws.spring.account.controller;

import com.ws.spring.account.domain.Account;
import com.ws.spring.account.dto.request.AccountCreateRequestDto;
import com.ws.spring.account.dto.request.CreditRequestDto;
import com.ws.spring.account.dto.request.DebitRequestDto;
import com.ws.spring.account.dto.request.TransferRequestDto;
import com.ws.spring.account.dto.response.CreditResponseDto;
import com.ws.spring.account.dto.response.DebitResponseDto;
import com.ws.spring.account.dto.response.TransferResponseDto;
import com.ws.spring.account.exception.CreditTransactionException;
import com.ws.spring.account.exception.DebitTransactionException;
import com.ws.spring.account.exception.ResourceNotFoundException;
import com.ws.spring.account.exception.TransferTransactionException;
import com.ws.spring.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

//@RequestMapping("/accounts")
@Slf4j
@Controller
public class AccountController {

    private final AccountService accountService;


    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{accountNumber}") // GET - /mypath/{xxxxx}
    public ResponseEntity<?> getByAccountNumber(@PathVariable String accountNumber) throws Exception {
        log.info("Controller layer looking for Account entity with accountNumber {}", accountNumber);
        //Validations

        Account account = accountService.getByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account not found"));

        return new ResponseEntity(account, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody AccountCreateRequestDto request) {
        log.info("Controller layer creating account with request {}", request);

        Account account = accountService.create(request);

        return new ResponseEntity(account, HttpStatus.CREATED);
    }

    @PostMapping("/credit")
    public ResponseEntity<?> doCredit(@RequestBody CreditRequestDto request) throws Exception {
        log.info("Controller layer requesting a credit with request {}", request);

        CreditResponseDto response = accountService.creditToBalance(request)
                .orElseThrow(() -> new CreditTransactionException(String.format("Problem doing credit for request '%s'", request)));

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/debit")
    public ResponseEntity<?> doDebit(@RequestBody DebitRequestDto request) throws Exception {
        log.info("Controller layer requesting a debit with request {}", request);

        DebitResponseDto response = accountService.debitFromBalance(request)
                .orElseThrow(() -> new DebitTransactionException(String.format("Problem doing debit for request '%s'", request)));

        return new ResponseEntity(response, HttpStatus.OK);
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> doTransfer(@RequestBody TransferRequestDto request) throws Exception {
        log.info("Controller layer requesting a transfer with request {}", request);

        TransferResponseDto response = accountService.transfer(request)
                .orElseThrow(() -> new TransferTransactionException(String.format("Problem doing transfer for request '%s'", request)));

        return new ResponseEntity(response, HttpStatus.OK);
    }

}
