package com.ws.spring.account.exception;

public class CreditTransactionException extends Exception {

    public CreditTransactionException(String message) {
        super(message);
    }

    public CreditTransactionException(String message, Throwable cause) {
        super(message, cause);
    }

}
