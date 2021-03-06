package com.ws.spring.account.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(HttpServletRequest request, final ResourceNotFoundException exception) {
        log.error("Resource not found", exception);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({NotEnoughFundsException.class, CreditTransactionException.class, DebitTransactionException.class, TransferTransactionException.class})
    public ResponseEntity<?> handleOperationsTransactionException(HttpServletRequest request, final Exception exception) {
        log.error("Conflict with resource in request", exception);
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ExceptionHandler({TransactionException.class})
    public ResponseEntity<?> handleTransactionException(HttpServletRequest request, final Exception exception) {
        log.error("Problems with communication", exception);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
