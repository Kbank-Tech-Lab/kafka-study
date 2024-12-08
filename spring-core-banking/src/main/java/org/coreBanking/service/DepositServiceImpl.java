package org.coreBanking.service;

import org.coreBanking.exception.CustomException;
import org.coreBanking.exception.ErrorCode;
import org.coreBanking.model.DepositAccount;
import org.coreBanking.repository.DepositAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DepositServiceImpl implements DepositService {

    public final DepositAccountRepository depositAccountRepository;

    public DepositServiceImpl(DepositAccountRepository depositAccountRepository) {
        this.depositAccountRepository = depositAccountRepository;
    }

    @Transactional
    public void depositToAccount(String accountNumber, Long amount) {
        // 1. 계좌 락 잡고 조회
        DepositAccount depositAccount = depositAccountRepository
            .findByAccountNumberWithLock(accountNumber)
            .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));

        // 2. 입금 처리
        depositAccount.setBalance(depositAccount.getBalance() + amount);

        // 3. 테이블 업데이트
        depositAccountRepository.save(depositAccount);
    }
}
