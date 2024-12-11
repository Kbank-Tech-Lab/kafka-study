package org.channel.repository;

import org.channel.dto.TransferLog;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TransferLogRepository extends JpaRepository<TransferLog, Long> {

    @Query(value = "select t " +
            "from TransferLog t " +
            "where t.fromAccountNumber = :accountId  " +
            "order by t.createdAt desc ")
    List<TransferLog> findTransferLogsByFromAccountId(UUID accountId, Pageable pageable);

    @Query(value = "select t " +
            "from TransferLog t " +
            "where t.toAccount = :accountId  " +
            "order by t.createdAt desc ")
    List<TransferLog> findTransferLogsByToAccountId(UUID accountId, Pageable pageable);

    @Query(value = "select t " +
            "from TransferLog t " +
            "where t.fromAccountNumber = :accountId or t.toAccount = :accountId " +
            "order by t.createdAt desc ")
    List<TransferLog> findTransferLogsByAccountId(UUID accountId);
}
