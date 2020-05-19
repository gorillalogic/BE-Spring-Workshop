package com.ws.spring.transaction.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDateTime;

@Data
@Entity
public class Transaction {

    @Id
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String accountNumberFrom;
    private String accountNumberTo;
    private String type;
    private double amount;
    private LocalDateTime dateTime;

}
