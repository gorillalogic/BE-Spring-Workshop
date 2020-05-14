package com.ws.spring.account.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Data
@Entity
public class Account {

    @Id
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String accountNumber;
    private double balance;

}
