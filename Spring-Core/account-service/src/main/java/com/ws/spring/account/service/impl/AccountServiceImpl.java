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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

//@Slf4j
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
        newAccount.setId(new Random().nextInt(Integer.MAX_VALUE) + 1);
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

//    @Transactional(
//            timeout = 45,
//            rollbackFor = { ResourceNotFoundException.class, NotEnoughFundsException.class }
//    )
    @Override
    public Optional<DebitResponseDto> debitFromBalance(DebitRequestDto debitRequest) throws ResourceNotFoundException, NotEnoughFundsException, DebitTransactionException {
//        log.info("Service layer doing a debit from balance with accountNumber {}", debitRequest.getAccountNumber());

        Account account = getByAccountNumber(debitRequest.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Account with accountNumber '%s' not found", debitRequest.getAccountNumber())));

        if(account.getBalance() < debitRequest.getAmount()) {
            throw new NotEnoughFundsException(String.format("Account with accountNumber '%s' does not have enough funds to perform  operation.", debitRequest.getAccountNumber()));
        }

        // Do the debit
        account.setBalance(account.getBalance() - debitRequest.getAmount());

        // Save changes
        accountRepository.save(account);

        // Register transaction
        // final String transactionNumber = String.valueOf(new Random().nextInt(Integer.MAX_VALUE) + 1);   // Mock data
        TransactionCreateRequestDto transactionCreateRequest = new TransactionCreateRequestDto();
        transactionCreateRequest.setAccountNumberFrom(debitRequest.getAccountNumber());
        transactionCreateRequest.setAmount(debitRequest.getAmount());
        transactionCreateRequest.setType(TransactionType.DEBIT.toString());

        final TransactionDto transactionDto = transactionService.registerTransaction(transactionCreateRequest)
                .orElseThrow(() -> new DebitTransactionException(String.format("Unable to complete debit transaction from accountNumber '%s'", debitRequest.getAccountNumber())));

//        log.info("Service layer applied debit from accountNumber {} of ${} with transaction #{}: ", debitRequest.getAccountNumber(), debitRequest.getAmount(), transactionNumber);

        DebitResponseDto response = new DebitResponseDto();
        response.setDateTime(LocalDateTime.now());
        response.setTransactionNumber(transactionDto.getId());
        response.setAccountNumber(debitRequest.getAccountNumber());
        response.setAmount(debitRequest.getAmount());

        return Optional.of(response);
    }

//    @Transactional(
//            timeout = 45,
//            rollbackFor = { ResourceNotFoundException.class }
//    )
    @Override
    public Optional<CreditResponseDto> creditToBalance(CreditRequestDto creditRequest) throws ResourceNotFoundException, CreditTransactionException {
//        log.info("Service layer doing a credit to balance with accountNumber {}", creditRequest.getAccountNumber());

        Account account = getByAccountNumber(creditRequest.getAccountNumber())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Account with accountNumber '%s' not found", creditRequest.getAccountNumber())));

        // Do the debit
        account.setBalance(account.getBalance() + creditRequest.getAmount());

        // Save changes
        accountRepository.save(account);

        // Register transaction
        // final String transactionNumber = String.valueOf(new Random().nextInt(Integer.MAX_VALUE) + 1);   // Mock data
        TransactionCreateRequestDto transactionCreateRequest = new TransactionCreateRequestDto();
        transactionCreateRequest.setAccountNumberTo(creditRequest.getAccountNumber());
        transactionCreateRequest.setAmount(creditRequest.getAmount());
        transactionCreateRequest.setType(TransactionType.CREDIT.toString());

        final TransactionDto transactionDto = transactionService.registerTransaction(transactionCreateRequest)
                .orElseThrow(() -> new CreditTransactionException(String.format("Unable to complete credit transaction to accountNumber '%s'", creditRequest.getAccountNumber())));

//        log.info("Service layer applied credit to accountNumber {} of ${} with transaction #{}: ", creditRequest.getAccountNumber(), creditRequest.getAmount(), transactionNumber);

        CreditResponseDto response = new CreditResponseDto();
        response.setDateTime(LocalDateTime.now());
        response.setTransactionNumber(transactionDto.getId());
        response.setAccountNumber(creditRequest.getAccountNumber());
        response.setAmount(creditRequest.getAmount());

        return Optional.of(response);
    }

//    @Transactional(
//            timeout = 45,
//            rollbackFor = { ResourceNotFoundException.class, NotEnoughFundsException.class, TransferTransactionException.class }
//    )
    @Override
    public Optional<TransferResponseDto> transfer(TransferRequestDto transferRequest) throws ResourceNotFoundException, NotEnoughFundsException, TransferTransactionException {
//        log.info("Service layer doing a transfer of ${} from accountNumber {} to accountNumber {}", transferRequest.getAmount(), transferRequest.getAccountNumberFrom(), transferRequest.getAccountNumberTo());

        // Do the debit
        Account accountFrom = getByAccountNumber(transferRequest.getAccountNumberFrom())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Account with accountNumber '%s' not found", transferRequest.getAccountNumberFrom())));

        if(accountFrom.getBalance() < transferRequest.getAmount()) {
            throw new NotEnoughFundsException(String.format("Account with accountNumber '%s' does not have enough funds to perform  operation.", transferRequest.getAccountNumberFrom()));
        }

        accountFrom.setBalance(accountFrom.getBalance() - transferRequest.getAmount());
        accountRepository.save(accountFrom);

//        // Doing this on purpose
//        if(accountFrom != null) {
//            Integer boooom = null;
//            throw new ResourceNotFoundException(String.format("Account with accountNumber '%s' not found", transferRequest.getAccountNumberTo()));
//        }

        // Do the credit
        Account accountTo = getByAccountNumber(transferRequest.getAccountNumberTo())
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Account with accountNumber '%s' not found", transferRequest.getAccountNumberTo())));

        accountTo.setBalance(accountTo.getBalance() + transferRequest.getAmount());
        accountRepository.save(accountTo);


        // Register transaction
        // final String transactionNumber = String.valueOf(new Random().nextInt(Integer.MAX_VALUE) + 1);   // Mock data
        TransactionCreateRequestDto transactionCreateRequest = new TransactionCreateRequestDto();
        transactionCreateRequest.setAccountNumberFrom(transferRequest.getAccountNumberFrom());
        transactionCreateRequest.setAccountNumberTo(transferRequest.getAccountNumberTo());
        transactionCreateRequest.setAmount(transferRequest.getAmount());
        transactionCreateRequest.setType(TransactionType.TRANSFER.toString());

        final TransactionDto transactionDto = transactionService.registerTransaction(transactionCreateRequest)
                .orElseThrow(() -> new TransferTransactionException(String.format("Unable to complete transfer transaction from accountNumber '%s' to accountNumber '%s'", transferRequest.getAccountNumberFrom(), transferRequest.getAccountNumberTo())));


//        log.info("Service layer applied transfer of ${} from accountNumber {} to accountNumber {} with transaction #{}: ",
//                transferRequest.getAmount(),
//                transferRequest.getAccountNumberFrom(),
//                transferRequest.getAccountNumberTo(),
//                transactionNumber);

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
