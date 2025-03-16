package org.consumer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.consumer.dto.MessageDto;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
@Component
@Slf4j
public class Consumer {
    private final static String TOPIC = "delay-transfer-topic";
    private final static String GROUP_ID = "group1";
    private final static int THREAD_COUNT = 10;

    private final DelayedTransferService delayedTransferService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(THREAD_COUNT);

    @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
    public void receive(ConsumerRecord<String, MessageDto> record) {
        final String userId = record.key();
        final MessageDto messageDto = record.value();
        log.info("Consumed message`s key: {}", userId);
        log.info("Consumed message`s value: {}", messageDto.toString());

        CompletableFuture.runAsync(() -> delayedTransferService.processTransfer(messageDto), executorService);
    }
}
