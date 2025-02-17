package org.consumer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.consumer.dto.MessageDto;
import org.consumer.repository.DelayedTransferRepository;
import org.consumer.util.ExternalApiClient;
import org.consumer.util.TransferStatus;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class DelayedTransferService {
    private final RedissonClient redissonClient;
    private final ExternalApiClient externalApiClient;
    private final DelayedTransferRepository delayedTransferRepository;


    public void processTransfer(MessageDto messageDto) {
        RLock lock = redissonClient.getLock(messageDto.getFromAccount());

        try {
            if (lock.tryLock()) {
                log.info("Lock acquired for account: {}", messageDto.getFromAccount());
                try {
                    delayedTransferRepository.findById(messageDto.getDelayedTransferId()).ifPresent(
                            delayedTransferRequest -> {
                                if (TransferStatus.PENDING.equals(delayedTransferRequest.getStatus())) {
                                    externalApiClient.callTransferApi(messageDto);
                                    log.info("Transfer request is sent to Core-Banking");
                                }
                            }
                    );
                } finally {
                    lock.unlock();
                    log.info("Lock released for account: {}", messageDto.getFromAccount());
                }
            }
        } catch (Exception e) {
            log.error("Could not acquire lock");
        }

        log.info("message consume end time: {}", System.currentTimeMillis());
    }
}
