package org.coreBanking.service;

import org.coreBanking.dto.TransferRequest;

public interface TransferService {

    void processTransfer(TransferRequest transferRequest);
}
