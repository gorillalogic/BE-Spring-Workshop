package com.ws.spring.account.dto.request;

import lombok.Data;

@Data
public class CreditRequestDto {

    private String accountNumber;
    private double amount;

}
