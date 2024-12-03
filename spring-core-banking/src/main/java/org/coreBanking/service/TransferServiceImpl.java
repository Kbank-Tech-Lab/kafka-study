package org.coreBanking.service;

import java.sql.Timestamp;
import org.coreBanking.dto.TransferRequest;
import org.coreBanking.model.TransferLog;
import org.coreBanking.repository.TransferLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferServiceImpl implements TransferService {

    private final String KbankCode = "089";

    private final DepositService depositService;
    private final WithdrawalService withdrawalService;
    private final TransferLogRepository transferLogRepository;

    public TransferServiceImpl(DepositService depositService, WithdrawalService withdrawalService,
        TransferLogRepository transferLogRepository) {
        this.depositService = depositService;
        this.withdrawalService = withdrawalService;
        this.transferLogRepository = transferLogRepository;
    }

    @Override
    @Transactional
    public void processTransfer(TransferRequest transferRequest) {
        if (KbankCode.equals(transferRequest.getToBankCode())) {
            // 자기 자신으로 송금 불가
            if (transferRequest.getFromAccount().equals(transferRequest.getToAccount())) {
                throw new RuntimeException("Transfer to same account impossible.");
            }

            // 출금
            withdrawalService.withdrawFromAccount(transferRequest.getFromAccount(), transferRequest.getTransferAmount());

            // 입금
            depositService.depositToAccount(transferRequest.getToAccount(), transferRequest.getTransferAmount());

            // 송금내역 적재
            TransferLog transferLog = new TransferLog();
            transferLog.setFromAccountNumber(transferRequest.getFromAccount());
            transferLog.setToAccount(transferRequest.getToAccount());
            transferLog.setTransferAmount(transferRequest.getTransferAmount());
            transferLog.setProcessedAt(new Timestamp(System.currentTimeMillis()));
            transferLogRepository.save(transferLog);

        } else {
            throw new RuntimeException("Transfer to other bank impossible.");  // 중소기업이라 타행이체 불가 ^^
        }
    }
}
