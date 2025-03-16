package org.coreBanking.controller;


import jakarta.validation.Valid;
import org.coreBanking.dto.TransferRequestDTO;
import org.coreBanking.exception.CustomException;
import org.coreBanking.exception.ErrorCode;
import org.coreBanking.model.DelayedTransferRequest.Status;
import org.coreBanking.service.DelayedTransferService;
import org.coreBanking.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {

    private final TransferService transferService;
    private final DelayedTransferService delayedTransferService;

    public TransferController(TransferService transferService,
        DelayedTransferService delayedTransferService) {
        this.transferService = transferService;
        this.delayedTransferService = delayedTransferService;
    }

    // 송금 API
    @PostMapping("/process")
    public ResponseEntity<String> processTransfer(@Valid @RequestBody TransferRequestDTO transferRequestDTO) {

        int transferLogId = transferService.saveTransferRequestLog(transferRequestDTO);

        try {
            transferService.processTransfer(transferRequestDTO);
        } catch (CustomException e) {
            if (e.getErrorCode().equals(ErrorCode.ACCOUNT_NOT_FOUND.getCode()) ||
                e.getErrorCode().equals(ErrorCode.CUSTOMER_NOT_FOUND.getCode()) ||
                e.getErrorCode().equals(ErrorCode.INSUFFICIENT_BALANCE.getCode()) ||
                e.getErrorCode().equals(ErrorCode.TRANSFER_TO_SAME_ACCOUNT.getCode()) ||
                e.getErrorCode().equals(ErrorCode.BANK_NOT_FOUND.getCode())) {

                // 만약 지연이체 건이었다면 처리 상태 실패로 변경
                if (transferRequestDTO.getDelayedTransferId() != null) {
                    delayedTransferService.updateStatus(transferRequestDTO.getDelayedTransferId(),
                        Status.FAILED);
                }
            }

            transferService.saveTransferResponseLog(transferLogId, false, e.getErrorCode());
            throw e;
        } catch (Exception e) {
            transferService.saveTransferResponseLog(transferLogId, false, ErrorCode.SYSTEM_ERROR.getCode());
            throw e;
        }

        // 만약 지연이체 건이었다면 처리 상태 완료로 변경
        if (transferRequestDTO.getDelayedTransferId() != null) {
            delayedTransferService.updateStatus(transferRequestDTO.getDelayedTransferId(),
                Status.COMPLETED);
        }
        transferService.saveTransferResponseLog(transferLogId, true, null);

        return ResponseEntity.ok("Transfer processed successfully.");
    }
}
