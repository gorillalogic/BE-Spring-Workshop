package com.ws.spring.transaction.dto.request;

import lombok.Data;

@Data
public class TransactionCreateRequestDto {

    private String accountNumberFrom;
    private String accountNumberTo;
    private String type;
    private double amount;

}
