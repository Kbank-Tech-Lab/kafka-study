package org.channel.repository;

import org.channel.domain.DepositAccount;
import org.channel.dto.DepositAccountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DepositAccountRepository extends JpaRepository<DepositAccount, UUID> {
    //SELECT d.id as depositAccountId, d.accountNumber, d.balance, c.id as customerId, c.name FROM DepositAccount d JOIN d.owner c
    @Query("SELECT new org.channel.dto.DepositAccountDto(d.id, d.accountNumber, d.balance, c.id, c.name) FROM DepositAccount d JOIN d.owner c")
    public List<DepositAccountDto> getDepositAccountDtoList();
}
