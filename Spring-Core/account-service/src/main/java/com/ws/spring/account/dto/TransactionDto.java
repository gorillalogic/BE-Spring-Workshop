package com.ws.spring.account.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDto {

    private Integer id;
    private String accountNumberFrom;
    private String accountNumberTo;
    private String type;
    private double amount;
    private LocalDateTime dateTime;

}
