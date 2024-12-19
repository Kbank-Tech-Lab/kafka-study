package org.coreBanking.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message) {
        super("Insufficient balance for account number: " + message);
    }
}
