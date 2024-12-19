package org.coreBanking.service;

import org.coreBanking.dto.DelayedTransferRequestDTO;
import org.coreBanking.model.DelayedTransferRequest;
import org.coreBanking.model.DelayedTransferRequest.Status;

public interface DelayedTransferService {
    DelayedTransferRequest createDelayedTransferRequest(DelayedTransferRequestDTO requestDTO);

    void updateStatus(Integer id, Status status);
}
