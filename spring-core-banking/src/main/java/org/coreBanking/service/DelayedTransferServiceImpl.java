package org.coreBanking.service;

import java.sql.Timestamp;
import org.coreBanking.dto.DelayedTransferRequestDTO;
import org.coreBanking.exception.CustomException;
import org.coreBanking.exception.ErrorCode;
import org.coreBanking.model.DelayedTransferRequest;
import org.coreBanking.model.DelayedTransferRequest.Status;
import org.coreBanking.repository.DelayedTransferRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DelayedTransferServiceImpl implements DelayedTransferService {

    private final DelayedTransferRequestRepository delayedTransferRequestRepository;

    public DelayedTransferServiceImpl(
        DelayedTransferRequestRepository delayedTransferRequestRepository) {
        this.delayedTransferRequestRepository = delayedTransferRequestRepository;
    }

    @Override
    @Transactional
    public DelayedTransferRequest createDelayedTransferRequest(DelayedTransferRequestDTO requestDTO) {

        DelayedTransferRequest request = new DelayedTransferRequest();

        request.setFromAccount(requestDTO.getFromAccount());
        request.setToBankCode(requestDTO.getToBankCode());
        request.setToAccount(requestDTO.getToAccount());
        request.setTransferAmount(requestDTO.getTransferAmount());
        request.setRequestedAt(new Timestamp(System.currentTimeMillis()));
        request.setStatus(Status.PENDING);

        return delayedTransferRequestRepository.save(request);
    }

    @Override
    @Transactional
    public void updateStatus(Integer id, Status status) {

        DelayedTransferRequest request = delayedTransferRequestRepository.findById(id)
            .orElseThrow(() -> new CustomException(ErrorCode.DELAYED_TRANSFER_NOT_FOUND));

        request.setStatus(status);

        if (status == Status.COMPLETED) {
            request.setProcessedAt(new Timestamp(System.currentTimeMillis()));
        }

        delayedTransferRequestRepository.save(request);
    }
}
