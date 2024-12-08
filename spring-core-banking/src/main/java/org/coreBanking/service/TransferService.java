package org.coreBanking.service;

import org.coreBanking.dto.TransferRequestDTO;

public interface TransferService {

    void processTransfer(TransferRequestDTO transferRequestDTO);
}
