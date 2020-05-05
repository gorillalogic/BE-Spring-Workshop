package com.ws.spring.account.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ws.spring.account.domain.Account;
import com.ws.spring.account.dto.request.CreditRequestDto;
import com.ws.spring.account.dto.request.DebitRequestDto;
import com.ws.spring.account.repository.AccountRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")                                                         // Defines the profiles to boot up
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)     // Defines a random port for the test
public class AccountControllerIntegrationTest {

    private MockMvc mockMvc;

    @MockBean
    private AccountRepository accountRepository;

    @Autowired
    private WebApplicationContext wac;


    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(this.wac)
                .build();
    }

    @Test
    public void getByAccountNumberSuccessTest() throws Exception {
        final String accountNumber = "abc";
        final Account expectedResult = new Account();
        expectedResult.setAccountNumber(accountNumber);

        when(accountRepository.getByAccountNumber(anyString())).thenReturn(Optional.of(expectedResult));

        mockMvc.perform(get("/" + accountNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.accountNumber", is(expectedResult.getAccountNumber())))
        ;
    }

    @Test
    public void getByAccountNumberExceptionTest() throws Exception {
        final String accountNumber = "abc";

        when(accountRepository.getByAccountNumber(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get("/" + accountNumber)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
        ;
    }

    @Test
    public void doCreditSuccessTest() throws Exception {
        final String accountNumber = "abc";
        final Account expectedResult = new Account();
        expectedResult.setAccountNumber(accountNumber);
        expectedResult.setBalance(100);

        CreditRequestDto request = new CreditRequestDto();
        request.setAccountNumber(accountNumber);
        request.setAmount(100);

        when(accountRepository.getByAccountNumber(anyString())).thenReturn(Optional.of(expectedResult));

        mockMvc.perform(post("/credit")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void doCreditNotFoundExceptionTest() throws Exception {
        final String accountNumber = "abc";
        final Account expectedResult = new Account();
        expectedResult.setAccountNumber(accountNumber);
        expectedResult.setBalance(100);

        CreditRequestDto request = new CreditRequestDto();
        request.setAccountNumber(accountNumber);
        request.setAmount(100);

        when(accountRepository.getByAccountNumber(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(post("/credit")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    public void doDebitSuccessTest() throws Exception {
        final String accountNumber = "abc";
        final Account expectedResult = new Account();
        expectedResult.setAccountNumber(accountNumber);
        expectedResult.setBalance(100);

        DebitRequestDto request = new DebitRequestDto();
        request.setAccountNumber(accountNumber);
        request.setAmount(100);

        when(accountRepository.getByAccountNumber(anyString())).thenReturn(Optional.of(expectedResult));

        mockMvc.perform(post("/debit")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void doDebitTransactionExceptionTest() throws Exception {
        final String accountNumber = "abc";
        final Account expectedResult = new Account();
        expectedResult.setAccountNumber(accountNumber);
        expectedResult.setBalance(0);

        DebitRequestDto request = new DebitRequestDto();
        request.setAccountNumber(accountNumber);
        request.setAmount(100);

        when(accountRepository.getByAccountNumber(anyString())).thenReturn(Optional.of(expectedResult));

        mockMvc.perform(post("/debit")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
        ;
    }

}
