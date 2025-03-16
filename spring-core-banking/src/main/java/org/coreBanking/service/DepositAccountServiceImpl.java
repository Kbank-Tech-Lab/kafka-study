package org.coreBanking.service;

import org.coreBanking.exception.CustomException;
import org.coreBanking.exception.ErrorCode;
import org.coreBanking.model.Customer;
import org.coreBanking.model.DepositAccount;
import org.coreBanking.repository.DepositAccountRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DepositAccountServiceImpl implements DepositAccountService {

    private final DepositAccountRepository depositAccountRepository;

    public DepositAccountServiceImpl(DepositAccountRepository depositAccountRepository) {
        this.depositAccountRepository = depositAccountRepository;
    }

    public DepositAccount createDepositAccount(Customer owner, String accountNumber, Long balance) {
        DepositAccount depositAccount = new DepositAccount(owner, accountNumber, balance);
        return depositAccountRepository.save(depositAccount);
    }

    public DepositAccount getDepositAccount(UUID id) {
        return depositAccountRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.ACCOUNT_NOT_FOUND));
    }
}

