package org.consumer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.consumer.dto.MessageDto;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
@Slf4j
public class Consumer {
    private final static String TOPIC = "delay-transfer-topic";
    private final static String GROUP_ID = "group1";

    private final RedissonClient redissonClient;
    private final DelayedTransferService delayedTransferService;

    @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
    public void receive(ConsumerRecord<String, MessageDto> record) {
        final String userId = record.key();
        final MessageDto messageDto = record.value();
        log.info("Consumed message`s key: {}", userId);
        log.info("Consumed message`s value: {}", messageDto.toString());

        RLock lock = redissonClient.getLock(messageDto.getFromAccount());

        try {
            if (lock.tryLock(5, 5, TimeUnit.SECONDS)) {
                log.info("Lock acquired for account: {}", messageDto.getFromAccount());
                try {
                    delayedTransferService.processTransfer(messageDto);
                } finally {
                    lock.unlock();
                    log.info("Lock released for account: {}", messageDto.getFromAccount());
                }
            }
        } catch (InterruptedException e) {
            log.error("Could not acquire lock");
        }
    }
}
