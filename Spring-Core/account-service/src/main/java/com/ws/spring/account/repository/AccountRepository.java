package com.ws.spring.account.repository;

import com.ws.spring.account.domain.Account;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AccountRepository {

    Optional<Account> getById(Integer id);
    Optional<Account> getByAccountNumber(String accountNumber);

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    Account save(Account account);

}
