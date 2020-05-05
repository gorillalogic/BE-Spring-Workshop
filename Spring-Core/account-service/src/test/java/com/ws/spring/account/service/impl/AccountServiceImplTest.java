package com.ws.spring.account.service.impl;

import com.ws.spring.account.domain.Account;
import com.ws.spring.account.repository.AccountRepository;
import com.ws.spring.account.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class AccountServiceImplTest {

    private AccountService accountService;

    @MockBean
    private AccountRepository accountRepository;

    @Before
    public void setup() {
        this.accountService = new AccountServiceImpl(accountRepository);
    }

    @Test
    public void getByIdSuccessTest() {
        final String accountNumber = "abc-123";
        final Account expectedResult = new Account();
        expectedResult.setAccountNumber(accountNumber);

        when(accountRepository.getByAccountNumber(anyString())).thenReturn(Optional.of(expectedResult));

        Account account = accountService.getByAccountNumber(accountNumber).orElse(null);

        assertNotNull(account);
        assertEquals(account.getAccountNumber(), accountNumber);
    }

}
