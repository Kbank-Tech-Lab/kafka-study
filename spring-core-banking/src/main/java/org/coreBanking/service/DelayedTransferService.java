package org.coreBanking.service;

import org.coreBanking.dto.DelayedTransferRequestDTO;
import org.coreBanking.model.DelayedTransferRequest;

public interface DelayedTransferService {
    DelayedTransferRequest createDelayedTransferRequest(DelayedTransferRequestDTO requestDTO);
}
