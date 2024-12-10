package org.coreBanking.repository;

import org.coreBanking.model.DelayedTransferRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DelayedTransferRequestRepository extends JpaRepository<DelayedTransferRequest, Integer> {

}
