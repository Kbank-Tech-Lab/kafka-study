package org.coreBanking.service;

import java.util.UUID;
import org.coreBanking.model.Customer;
import org.coreBanking.model.DepositAccount;

public interface DepositAccountService {

    DepositAccount createDepositAccount(Customer owner, String accountNumber, Long balance);
    DepositAccount getDepositAccount(UUID id);
}
