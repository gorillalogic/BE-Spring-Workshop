package com.ws.spring.account.dto.request;

import lombok.Data;

@Data
public class TransferRequestDto {

    private String accountNumberFrom;
    private String accountNumberTo;
    private String concept;
    private double amount;

}
