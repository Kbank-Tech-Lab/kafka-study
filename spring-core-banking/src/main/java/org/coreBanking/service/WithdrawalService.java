package org.coreBanking.service;

public interface WithdrawalService {

    void withdrawFromAccount(String accountNumber, Long amount);
}
