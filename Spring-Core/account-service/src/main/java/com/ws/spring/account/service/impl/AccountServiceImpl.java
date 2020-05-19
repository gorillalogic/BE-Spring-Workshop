package com.ws.spring.account.service.impl;

import com.ws.spring.account.domain.Account;
import com.ws.spring.account.domain.TransactionType;
import com.ws.spring.account.dto.TransactionDto;
import com.ws.spring.account.dto.request.*;
import com.ws.spring.account.dto.response.CreditResponseDto;
import com.ws.spring.account.dto.response.DebitResponseDto;
import com.ws.spring.account.dto.response.TransferResponseDto;
import com.ws.spring.account.exception.*;
import com.ws.spring.account.repository.AccountRepository;
import com.ws.spring.account.service.AccountService;
import com.ws.spring.account.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final TransactionService transactionService;


    public AccountServiceImpl(
            AccountRepository accountRepository,
            TransactionService transactionService) {

        this.accountRepository = accountRepository;
        this.transactionService = transactionService;
    }

    @Override
    public Optional<Account> getById(Integer id) {
        return accountRepository.getById(id);
    }

    @Override
    public Optional<Account> getByAccountNumber(String accountNumber) {
        return accountRepository.getByAccountNumber(accountNumber);
    }

    @Override
    public Account create(AccountCreateRequestDto request) {
        Account newAccount = new Account();
        newAccount.setId(new Random().nextInt(Integer.MAX_VALUE) + 1);
        newAccount.setAccountNumber(String.valueOf(new Random().nextInt(Integer.MAX_VALUE) + 1));
        newAccount.setBalance(request.getBalance());

        return accountRepository.save(newAccount);
    }

    @Override
    public double getBalance(String accountNumber) throws ResourceNotFoundException {
        Account account = getByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException(""));

        return account.getBalance();
    }

    @Transactional(
            timeout = 45,
            rollbackFor = {
                    ResourceNotFoundException.class,
                    NotEnoughFundsException.class,
                    DebitTransactionException.class,
                    TransactionException.class,
            }
            , propagation = Propagation.NESTED
    )
    @Override
    public Optional<DebitResponseDto> debitFromBalance(DebitRequestDto debitRequest) throws ResourceNotFoundException, NotEnoughFundsException, DebitTransactionException, TransactionException {

        Account account = getByAccountNumber(debitRequest.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Account with accountNumber '%s' not found", debitRequest.getAccountNumber())));

        if(account.getBalance() < debitRequest.getAmount()) {
            throw new NotEnoughFundsException(String.format("Account with accountNumber '%s' does not have enough funds to perform  operation.", debitRequest.getAccountNumber()));
        }

        // Do the debit
        account.setBalance(account.getBalance() - debitRequest.getAmount());

        // Save changes
        accountRepository.save(account);

//        // Doing this on purpose
//        throw new RuntimeException();

        // Register transaction
        TransactionCreateRequestDto transactionCreateRequest = new TransactionCreateRequestDto();
        transactionCreateRequest.setAccountNumberFrom(debitRequest.getAccountNumber());
        transactionCreateRequest.setAmount(debitRequest.getAmount());
        transactionCreateRequest.setType(TransactionType.DEBIT.toString());

        final TransactionDto transactionDto = transactionService.registerTransactionRestTemplate(transactionCreateRequest)
                .orElseThrow(() -> new DebitTransactionException(String.format("Unable to complete debit transaction from accountNumber '%s'", debitRequest.getAccountNumber())));

        DebitResponseDto response = new DebitResponseDto();
        response.setDateTime(LocalDateTime.now());
        response.setTransactionNumber(transactionDto.getId());
        response.setAccountNumber(debitRequest.getAccountNumber());
        response.setAmount(debitRequest.getAmount());

        return Optional.of(response);
    }

    @Transactional(
            timeout = 45,
            rollbackFor = {
                    ResourceNotFoundException.class,
                    CreditTransactionException.class,
                    TransactionException.class,
            }
            , propagation = Propagation.NESTED
    )
    @Override
    public Optional<CreditResponseDto> creditToBalance(CreditRequestDto creditRequest) throws ResourceNotFoundException, CreditTransactionException, TransactionException {

        Account account = getByAccountNumber(creditRequest.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Account with accountNumber '%s' not found", creditRequest.getAccountNumber())));

        // Do the debit
        account.setBalance(account.getBalance() + creditRequest.getAmount());

        // Save changes
        accountRepository.save(account);

        // Doing this on purpose
//        throw new RuntimeException();

        // Register transaction
        TransactionCreateRequestDto transactionCreateRequest = new TransactionCreateRequestDto();
        transactionCreateRequest.setAccountNumberTo(creditRequest.getAccountNumber());
        transactionCreateRequest.setAmount(creditRequest.getAmount());
        transactionCreateRequest.setType(TransactionType.CREDIT.toString());

        final TransactionDto transactionDto = transactionService.registerTransaction(transactionCreateRequest)
                .orElseThrow(() -> new CreditTransactionException(String.format("Unable to complete credit transaction to accountNumber '%s'", creditRequest.getAccountNumber())));

        CreditResponseDto response = new CreditResponseDto();
        response.setDateTime(LocalDateTime.now());
        response.setTransactionNumber(transactionDto.getId());
        response.setAccountNumber(creditRequest.getAccountNumber());
        response.setAmount(creditRequest.getAmount());

        return Optional.of(response);
    }

    @Transactional(
            timeout = 45,
            rollbackFor = {
                    TransferTransactionException.class,
                    TransactionException.class,
            }
    )
    @Override
    public Optional<TransferResponseDto> transfer(TransferRequestDto transferRequest) throws TransferTransactionException, TransactionException {

        // Do the debit
        DebitRequestDto debitRequestDto = new DebitRequestDto();
        debitRequestDto.setAccountNumber(transferRequest.getAccountNumberFrom());
        debitRequestDto.setAmount(transferRequest.getAmount());

        try {
            this.debitFromBalance(debitRequestDto).orElseThrow(() -> new TransferTransactionException(String.format("Unable to complete transfer transaction from accountNumber '%s' to accountNumber '%s'", transferRequest.getAccountNumberFrom(), transferRequest.getAccountNumberTo())));
        } catch (ResourceNotFoundException | NotEnoughFundsException | DebitTransactionException e) {
            throw new TransferTransactionException(String.format("Unable to complete transfer transaction from accountNumber '%s' to accountNumber '%s'", transferRequest.getAccountNumberFrom(), transferRequest.getAccountNumberTo()));
        }

//        // Doing this on purpose
        CreditRequestDto creditRequestDto = new CreditResponseDto();
        creditRequestDto.setAccountNumber(transferRequest.getAccountNumberTo());
        creditRequestDto.setAmount(transferRequest.getAmount());

        try {
            this.creditToBalance(creditRequestDto).orElseThrow(() -> new TransferTransactionException(String.format("Unable to complete transfer transaction from accountNumber '%s' to accountNumber '%s'", transferRequest.getAccountNumberFrom(), transferRequest.getAccountNumberTo())));
        } catch (ResourceNotFoundException | CreditTransactionException e) {
            throw new TransferTransactionException(String.format("Unable to complete transfer transaction from accountNumber '%s' to accountNumber '%s'", transferRequest.getAccountNumberFrom(), transferRequest.getAccountNumberTo()));
        }


        // Register transaction
        TransactionCreateRequestDto transactionCreateRequest = new TransactionCreateRequestDto();
        transactionCreateRequest.setAccountNumberFrom(transferRequest.getAccountNumberFrom());
        transactionCreateRequest.setAccountNumberTo(transferRequest.getAccountNumberTo());
        transactionCreateRequest.setAmount(transferRequest.getAmount());
        transactionCreateRequest.setType(TransactionType.TRANSFER.toString());

        final TransactionDto transactionDto = transactionService.registerTransaction(transactionCreateRequest)
                .orElseThrow(() -> new TransferTransactionException(String.format("Unable to complete transfer transaction from accountNumber '%s' to accountNumber '%s'", transferRequest.getAccountNumberFrom(), transferRequest.getAccountNumberTo())));

        TransferResponseDto response = new TransferResponseDto();
        response.setTransactionNumber(transactionDto.getId());
        response.setAccountNumberFrom(transferRequest.getAccountNumberFrom());
        response.setAccountNumberTo(transferRequest.getAccountNumberTo());
        response.setAmount(transferRequest.getAmount());
        response.setConcept(transferRequest.getConcept());
        response.setDateTime(LocalDateTime.now());

        return Optional.of(response);
    }

}
