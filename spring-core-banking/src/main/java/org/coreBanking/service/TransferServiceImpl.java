package org.coreBanking.service;

import java.sql.Timestamp;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.coreBanking.dto.TransferRequestDTO;
import org.coreBanking.exception.CustomException;
import org.coreBanking.exception.ErrorCode;
import org.coreBanking.model.DelayedTransferRequest.Status;
import org.coreBanking.model.TransferLog;
import org.coreBanking.repository.TransferLogRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferServiceImpl implements TransferService {

    private final String KbankCode = "089";

    private final DepositService depositService;
    private final WithdrawalService withdrawalService;
    private final OtherBankService otherBankService;
    private final DelayedTransferService delayedTransferService;
    private final TransferLogRepository transferLogRepository;

    public TransferServiceImpl(DepositService depositService, WithdrawalService withdrawalService,
        OtherBankService otherBankService, DelayedTransferService delayedTransferService,
        TransferLogRepository transferLogRepository) {
        this.depositService = depositService;
        this.withdrawalService = withdrawalService;
        this.otherBankService = otherBankService;
        this.delayedTransferService = delayedTransferService;
        this.transferLogRepository = transferLogRepository;
    }

    @Override
    @Transactional
    public void processTransfer(TransferRequestDTO transferRequestDTO) {
        System.out.println("Request: " + transferRequestDTO);
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

            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                // 타행 서비스 호출
                otherBankService.transferOtherBank(
                    transferRequestDTO.getToBankCode(),
                    transferRequestDTO.getToAccount(),
                    transferRequestDTO.getTransferAmount()
                );
            }).orTimeout(60, TimeUnit.SECONDS); // 타임아웃 설정

            try {
                future.get(); // 타임아웃이나 예외 발생 시 확인
            } catch (ExecutionException | CompletionException e) {
                Throwable cause = e.getCause(); // 원본 예외 가져오기
                if (cause instanceof TimeoutException) {
                    throw new CustomException(ErrorCode.BANK_TRANSFER_TIMEOUT);
                } else if (cause instanceof CustomException) {
                    throw (CustomException) cause; // CustomException 그대로 던지기
                } else {
                    throw new RuntimeException(cause);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new CustomException(ErrorCode.BANK_TRANSFER_INTERRUPTED);
            }

            // 송금 내역 적재
            _saveTransferLog(transferRequestDTO);
        }

        // 만약 지연이체 건이었다면 처리 상태 완료로 변경
        if (transferRequestDTO.getDelayedTransferId() != null) {
            delayedTransferService.updateStatus(transferRequestDTO.getDelayedTransferId(),
                Status.COMPLETED);
        }
    }

    private void _saveTransferLog(TransferRequestDTO transferRequestDTO) {
        TransferLog transferLog = new TransferLog();

        transferLog.setFromAccountNumber(transferRequestDTO.getFromAccount());
        transferLog.setToBankCode(transferRequestDTO.getToBankCode());
        transferLog.setToAccount(transferRequestDTO.getToAccount());
        transferLog.setTransferAmount(transferRequestDTO.getTransferAmount());
        transferLog.setProcessedAt(new Timestamp(System.currentTimeMillis()));

        transferLogRepository.save(transferLog);
    }
}
