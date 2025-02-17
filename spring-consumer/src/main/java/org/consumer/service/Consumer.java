package org.consumer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.consumer.dto.MessageDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    public void receive(ConsumerRecords<String, MessageDto> records) {
        records.forEach(record -> {
            String userId = record.key();
            int partition = record.partition();

            MessageDto messageDto = record.value();
            log.info("message consume start time: {}", System.currentTimeMillis());
            log.info("Consumed message`s key: {}", userId);
            log.info("Consumed message`s partition: {}", partition);
            log.info("Consumed message`s value: {}", messageDto.toString());

            CompletableFuture.runAsync(() -> delayedTransferService.processTransfer(messageDto), executorService);
        });
    }
}
