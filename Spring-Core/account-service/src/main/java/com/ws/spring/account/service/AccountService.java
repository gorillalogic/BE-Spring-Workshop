package com.ws.spring.account.service;

import com.ws.spring.account.domain.Account;
import com.ws.spring.account.dto.request.AccountCreateRequestDto;
import com.ws.spring.account.dto.request.CreditRequestDto;
import com.ws.spring.account.dto.request.DebitRequestDto;
import com.ws.spring.account.dto.request.TransferRequestDto;
import com.ws.spring.account.dto.response.CreditResponseDto;
import com.ws.spring.account.dto.response.DebitResponseDto;
import com.ws.spring.account.dto.response.TransferResponseDto;
import com.ws.spring.account.exception.*;

import java.util.Optional;

public interface AccountService {

    Optional<Account> getById(Integer id) throws ResourceNotFoundException;
    Optional<Account> getByAccountNumber(String accountNumber);
    Account create(AccountCreateRequestDto request);
    double getBalance(String accountNumber) throws ResourceNotFoundException;
    Optional<DebitResponseDto> debitFromBalance(DebitRequestDto debitRequest) throws ResourceNotFoundException, NotEnoughFundsException, DebitTransactionException;
    Optional<CreditResponseDto> creditToBalance(CreditRequestDto creditRequest) throws ResourceNotFoundException, CreditTransactionException;
    Optional<TransferResponseDto> transfer(TransferRequestDto transferRequest) throws ResourceNotFoundException, NotEnoughFundsException, TransferTransactionException;

}
