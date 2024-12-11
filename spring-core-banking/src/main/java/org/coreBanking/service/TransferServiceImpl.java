package org.coreBanking.service;

import java.sql.Timestamp;
import java.time.Clock;
import java.time.Instant;
import org.coreBanking.dto.TransferRequestDTO;
import org.coreBanking.exception.CustomException;
import org.coreBanking.exception.ErrorCode;
import org.coreBanking.model.TransferLog;
import org.coreBanking.repository.TransferLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferServiceImpl implements TransferService {

    private final String KbankCode = "089";

    private final Clock clock;
    private final DepositService depositService;
    private final WithdrawalService withdrawalService;
    private final OtherBankService otherBankService;
    private final TransferLogRepository transferLogRepository;

    public TransferServiceImpl(Clock clock, DepositService depositService, WithdrawalService withdrawalService,
        OtherBankService otherBankService,
        TransferLogRepository transferLogRepository) {
        this.clock = clock;
        this.depositService = depositService;
        this.withdrawalService = withdrawalService;
        this.otherBankService = otherBankService;
        this.transferLogRepository = transferLogRepository;
    }

    @Override
    @Transactional
    public void processTransfer(TransferRequestDTO transferRequestDTO) {
        if (KbankCode.equals(transferRequestDTO.getToBankCode())) {
            // 자기 자신에게 송금 불가
            if (transferRequestDTO.getFromAccount().equals(transferRequestDTO.getToAccount())) {
                throw new CustomException(ErrorCode.TRANSFER_TO_SAME_ACCOUNT);
            }

            // 출금
            withdrawalService.withdrawFromAccount(transferRequestDTO.getFromAccount(), transferRequestDTO.getTransferAmount());

            // 입금
            depositService.depositToAccount(transferRequestDTO.getToAccount(), transferRequestDTO.getTransferAmount());

            // 송금 내역 적재
            _saveTransferLog(transferRequestDTO);

        } else {
            // 출금
            withdrawalService.withdrawFromAccount(transferRequestDTO.getFromAccount(), transferRequestDTO.getTransferAmount());

            // 타행 서비스 호출
            otherBankService.transferOtherBank(transferRequestDTO.getToBankCode(), transferRequestDTO.getToAccount(), transferRequestDTO.getTransferAmount());

            // 송금 내역 적재
            _saveTransferLog(transferRequestDTO);
        }
    }

    private void _saveTransferLog(TransferRequestDTO transferRequestDTO) {
        TransferLog transferLog = new TransferLog();

        transferLog.setFromAccountNumber(transferRequestDTO.getFromAccount());
        transferLog.setToBankCode(transferRequestDTO.getToBankCode());
        transferLog.setToAccount(transferRequestDTO.getToAccount());
        transferLog.setTransferAmount(transferRequestDTO.getTransferAmount());
        transferLog.setProcessedAt(Timestamp.from(Instant.now(clock)));

        transferLogRepository.save(transferLog);
    }
}
