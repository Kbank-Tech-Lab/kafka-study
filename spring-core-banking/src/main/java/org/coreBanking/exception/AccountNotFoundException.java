package org.coreBanking.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String accountNumber) {
        super("Account not found for account number: " + accountNumber);
    }
}