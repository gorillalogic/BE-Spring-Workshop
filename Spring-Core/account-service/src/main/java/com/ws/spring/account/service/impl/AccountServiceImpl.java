package com.ws.spring.account.service.impl;

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
import com.ws.spring.account.repository.AccountRepository;
import com.ws.spring.account.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

//@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;


    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Optional<Account> getById(String id) {
//        log.info("Service layer looking for Account entity with id {}", id);

        return accountRepository.getById(id);
    }

    @Override
    public Optional<Account> getByAccountNumber(String accountNumber) {
//        log.info("Service layer looking for Account entity with accountNumber {}",  accountNumber);

        return accountRepository.getByAccountNumber(accountNumber);
    }

    @Override
    public Account create(AccountCreateRequestDto request) {
//        log.info("Service layer creating Account with request {}", request);

        Account newAccount = new Account();
        newAccount.setAccountNumber(String.valueOf(new Random().nextInt(Integer.MAX_VALUE) + 1));
        newAccount.setBalance(request.getBalance());

        return accountRepository.save(newAccount);
    }

    @Override
    public double getBalance(String accountNumber) throws ResourceNotFoundException {
//        log.info("Service layer getting balance with accountNumber {}", accountNumber);

        Account account = getByAccountNumber(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException(""));

        return account.getBalance();
    }

    @Override
    public Optional<DebitResponseDto> debitFromBalance(DebitRequestDto debitRequest) throws ResourceNotFoundException, NotEnoughFundsException {
//        log.info("Service layer doing a debit from balance with accountNumber {}", debitRequest.getAccountNumber());

        Account account = getByAccountNumber(debitRequest.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Account with accountNumber '%s' not found", debitRequest.getAccountNumber())));

        if(account.getBalance() < debitRequest.getAmount()) {
            throw new NotEnoughFundsException("Account with accountNumber '%s' does not have enough funds to perform  operation.");
        }

        // Do the debit
        account.setBalance(account.getBalance() - debitRequest.getAmount());

        // Save changes
        accountRepository.save(account);

        // Register transaction
        // ...
        final String transactionNumber = String.valueOf(new Random().nextInt(Integer.MAX_VALUE) + 1);   // Mock data

//        log.info("Service layer applied debit from accountNumber {} of ${} with transaction #{}: ", debitRequest.getAccountNumber(), debitRequest.getAmount(), transactionNumber);

        DebitResponseDto response = new DebitResponseDto();
        response.setDateTime(LocalDateTime.now());
        response.setTransactionNumber(transactionNumber);
        response.setAccountNumber(debitRequest.getAccountNumber());
        response.setAmount(debitRequest.getAmount());

        return Optional.of(response);
    }

    @Override
    public Optional<CreditResponseDto> creditToBalance(CreditRequestDto creditRequest) throws ResourceNotFoundException {
//        log.info("Service layer doing a credit to balance with accountNumber {}", creditRequest.getAccountNumber());

        Account account = getByAccountNumber(creditRequest.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Account with accountNumber '%s' not found", creditRequest.getAccountNumber())));

        // Do the debit
        account.setBalance(account.getBalance() + creditRequest.getAmount());

        // Save changes
        accountRepository.save(account);

        // Register transaction
        // ...
        final String transactionNumber = String.valueOf(new Random().nextInt(Integer.MAX_VALUE) + 1);   // Mock data

//        log.info("Service layer applied credit to accountNumber {} of ${} with transaction #{}: ", creditRequest.getAccountNumber(), creditRequest.getAmount(), transactionNumber);

        CreditResponseDto response = new CreditResponseDto();
        response.setDateTime(LocalDateTime.now());
        response.setTransactionNumber(transactionNumber);
        response.setAccountNumber(creditRequest.getAccountNumber());
        response.setAmount(creditRequest.getAmount());

        return Optional.of(response);
    }

    @Override
    public Optional<TransferResponseDto> transfer(TransferRequestDto transferRequest) throws ResourceNotFoundException, NotEnoughFundsException {
//        log.info("Service layer doing a transfer of ${} from accountNumber {} to accountNumber {}", transferRequest.getAmount(), transferRequest.getAccountNumberFrom(), transferRequest.getAccountNumberTo());

        Account accountFrom = getByAccountNumber(transferRequest.getAccountNumberFrom())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Account with accountNumber '%s' not found", transferRequest.getAccountNumberFrom())));

        Account accountTo = getByAccountNumber(transferRequest.getAccountNumberTo())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Account with accountNumber '%s' not found", transferRequest.getAccountNumberTo())));

        if(accountFrom.getBalance() < transferRequest.getAmount()) {
            throw new NotEnoughFundsException("Account with accountNumber '%s' does not have enough funds to perform  operation.");
        }

        // Do the debit
        accountFrom.setBalance(accountFrom.getBalance() - transferRequest.getAmount());

        // Do the credit
        accountTo.setBalance(accountTo.getBalance() + transferRequest.getAmount());


        // Save changes
        accountRepository.save(accountFrom);
        accountRepository.save(accountTo);

        // Register transaction
        // ...
        final String transactionNumber = String.valueOf(new Random().nextInt(Integer.MAX_VALUE) + 1);   // Mock data


//        log.info("Service layer applied transfer of ${} from accountNumber {} to accountNumber {} with transaction #{}: ",
//                transferRequest.getAmount(),
//                transferRequest.getAccountNumberFrom(),
//                transferRequest.getAccountNumberTo(),
//                transactionNumber);

        TransferResponseDto response = new TransferResponseDto();
        response.setDateTime(LocalDateTime.now());
        response.setTransactionNumber(transactionNumber);
        response.setAccountNumberFrom(transferRequest.getAccountNumberFrom());
        response.setAccountNumberTo(transferRequest.getAccountNumberTo());
        response.setAmount(transferRequest.getAmount());

        return Optional.of(response);
    }

}
