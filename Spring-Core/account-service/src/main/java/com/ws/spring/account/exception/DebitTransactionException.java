package com.ws.spring.account.exception;

public class DebitTransactionException extends Exception {

    public DebitTransactionException(String message) {
        super(message);
    }

    public DebitTransactionException(String message, Throwable cause) {
        super(message, cause);
    }

}
