package com.ws.spring.account.service;

import com.ws.spring.account.domain.Account;
import com.ws.spring.account.dto.request.AccountCreateRequestDto;
import com.ws.spring.account.dto.request.CreditRequestDto;
import com.ws.spring.account.dto.request.DebitRequestDto;
import com.ws.spring.account.dto.request.TransferRequestDto;
import com.ws.spring.account.dto.response.CreditResponseDto;
import com.ws.spring.account.dto.response.DebitResponseDto;
import com.ws.spring.account.dto.response.TransferResponseDto;
import com.ws.spring.account.exception.NotEnoughFundsException;
import com.ws.spring.account.exception.ResourceNotFoundException;

import java.util.Optional;

public interface AccountService {

    Optional<Account> getById(String id) throws ResourceNotFoundException;
    Optional<Account> getByAccountNumber(String accountNumber);
    Account create(AccountCreateRequestDto request);
    double getBalance(String accountNumber) throws ResourceNotFoundException;
    Optional<DebitResponseDto> debitFromBalance(DebitRequestDto debitRequest) throws ResourceNotFoundException, NotEnoughFundsException;
    Optional<CreditResponseDto> creditToBalance(CreditRequestDto creditRequest) throws ResourceNotFoundException;
    Optional<TransferResponseDto> transfer(TransferRequestDto transferRequest) throws ResourceNotFoundException, NotEnoughFundsException;

}
