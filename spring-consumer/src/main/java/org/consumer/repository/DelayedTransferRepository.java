package org.consumer.repository;

import org.consumer.entity.DelayedTransferRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DelayedTransferRepository extends JpaRepository<DelayedTransferRequest, Long> {
}
