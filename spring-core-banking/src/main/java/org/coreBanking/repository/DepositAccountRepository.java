package org.coreBanking.repository;

import jakarta.persistence.LockModeType;
import java.util.Optional;
import java.util.UUID;
import org.coreBanking.model.DepositAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositAccountRepository extends JpaRepository<DepositAccount, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT d FROM DepositAccount d WHERE d.accountNumber = :accountNumber")
    Optional<DepositAccount> findByAccountNumberWithLock(String accountNumber);
}
