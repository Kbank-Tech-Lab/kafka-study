package org.consumer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.consumer.dto.MessageDto;
import org.consumer.repository.DelayedTransferRepository;
import org.consumer.util.ExternalApiClient;
import org.consumer.util.TransferStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DelayedTransferService {
    private final ExternalApiClient externalApiClient;
    private final DelayedTransferRepository delayedTransferRepository;

    public void processTransfer(MessageDto messageDto) {
        delayedTransferRepository.findById(messageDto.getId()).ifPresent(
                delayedTransferRequest -> {
                    if (TransferStatus.PENDING.equals(delayedTransferRequest.getStatus())) {
                        externalApiClient.callTransferApi(messageDto);
                        log.info("Transfer request is sent to Core-Banking");
                    }
                }
        );
    }
}
