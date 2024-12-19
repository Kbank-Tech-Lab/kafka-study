package org.coreBanking.service;

import org.coreBanking.exception.AccountNotFoundException;
import org.coreBanking.exception.InsufficientBalanceException;
import org.coreBanking.model.DepositAccount;
import org.coreBanking.repository.DepositAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WithdrawalServiceImpl implements WithdrawalService {

    public final DepositAccountRepository depositAccountRepository;

    public WithdrawalServiceImpl(DepositAccountRepository depositAccountRepository) {
        this.depositAccountRepository = depositAccountRepository;
    }

    @Transactional
    public void withdrawFromAccount(String accountNumber, Long amount) {
        // 1. 계좌 락 잡고 조회
        DepositAccount depositAccount = depositAccountRepository
            .findByAccountNumberWithLock(accountNumber)
            .orElseThrow(() -> new AccountNotFoundException(accountNumber));

        // 2. 출금 처리
        if (depositAccount.getBalance() < amount) {
            throw new InsufficientBalanceException(accountNumber);
        }
        depositAccount.setBalance(depositAccount.getBalance() - amount);

        // 3. 테이블 업데이트
        depositAccountRepository.save(depositAccount);
    }
}
