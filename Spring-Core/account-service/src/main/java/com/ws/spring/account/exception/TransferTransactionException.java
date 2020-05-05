package com.ws.spring.account.exception;

public class TransferTransactionException extends Exception {

    public TransferTransactionException(String message) {
        super(message);
    }

    public TransferTransactionException(String message, Throwable cause) {
        super(message, cause);
    }

}
