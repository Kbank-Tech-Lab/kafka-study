package org.coreBanking.service;

import org.coreBanking.dto.TransferRequestDTO;

public interface TransferService {

    void processTransfer(TransferRequestDTO transferRequestDTO);
    int saveTransferRequestLog(TransferRequestDTO transferRequestDTO);
    void saveTransferResponseLog(int id, boolean isCompleted, String errorCode);
}
