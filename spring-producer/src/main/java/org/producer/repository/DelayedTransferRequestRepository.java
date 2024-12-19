package org.producer.repository;

import org.producer.dto.DelayedTransferRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DelayedTransferRequestRepository extends JpaRepository<DelayedTransferRequest, Integer> {
    public List<DelayedTransferRequest> findByStatusOrderByRequestedAtAsc(DelayedTransferRequest.Status status);
}
