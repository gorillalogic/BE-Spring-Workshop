package com.ws.spring.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ws.spring.account.domain.Account;
import com.ws.spring.account.dto.request.CreditRequestDto;
import com.ws.spring.account.dto.response.CreditResponseDto;
import com.ws.spring.account.service.AccountService;
import org.apache.tomcat.jni.Local;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AccountService accountService;


    @Test
    public void getByAccountNumberSuccessTest() throws Exception {
        final String accountNumber = "abc";
        final Account expectedResult = new Account();
        expectedResult.setAccountNumber(accountNumber);

        when(accountService.getByAccountNumber(anyString())).thenReturn(Optional.of(expectedResult));

        mockMvc.perform(get(String.format("/%s", accountNumber))
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

        when(accountService.getByAccountNumber(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get(String.format("/%s", accountNumber))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
        ;
    }

    @Test
    public void getByIdFailTest() throws Exception {
        final String accountNumber = "abc-123";

        when(accountService.getByAccountNumber(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(get(String.format("/%s", accountNumber))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().is4xxClientError())
        ;
    }

    @Test
    public void doCreditSuccessTest() throws Exception {
        final String accountNumber = "abc";
        final CreditRequestDto request = new CreditRequestDto();
        request.setAccountNumber(accountNumber);
        request.setAmount(100);

        final CreditResponseDto expectedResponse = new CreditResponseDto();
        expectedResponse.setDateTime(LocalDateTime.now());
        expectedResponse.setTransactionNumber("t1");
        expectedResponse.setAccountNumber(request.getAccountNumber());
        expectedResponse.setAmount(request.getAmount());

        when(accountService.creditToBalance(any())).thenReturn(Optional.of(expectedResponse));

        mockMvc.perform(post("/credit")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
        ;
    }

    @Test
    public void doCreditExceptionTest() throws Exception {
        final String accountNumber = "abc";
        final CreditRequestDto request = new CreditRequestDto();
        request.setAccountNumber(accountNumber);
        request.setAmount(100);

        when(accountService.creditToBalance(any())).thenReturn(Optional.empty());

        mockMvc.perform(post("/credit")
                .content(new ObjectMapper().writeValueAsString(request))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict())
        ;
    }

}
