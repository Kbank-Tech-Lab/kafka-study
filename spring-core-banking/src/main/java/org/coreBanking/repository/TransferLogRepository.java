package org.coreBanking.repository;

import org.coreBanking.model.TransferLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferLogRepository extends JpaRepository<TransferLog, Long> {

}
