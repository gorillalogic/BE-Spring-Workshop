package com.ws.spring.account.exception;

public class NotEnoughFundsException extends Exception {

    public NotEnoughFundsException(String message) {
        super(message);
    }

    public NotEnoughFundsException(String message, Throwable cause) {
        super(message, cause);
    }

}
