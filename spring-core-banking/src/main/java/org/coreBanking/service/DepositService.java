package org.coreBanking.service;

public interface DepositService {

    void depositToAccount(String accountNumber, Long amount);
}
