package com.ws.spring.account.dto.request;

import lombok.Data;

@Data
public class DebitRequestDto {

    private String accountNumber;
    private double amount;

}
