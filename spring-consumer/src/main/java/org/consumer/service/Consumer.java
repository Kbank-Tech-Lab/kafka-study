package org.consumer.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.consumer.dto.MessageDto;
import org.consumer.repository.DelayedTransferRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class Consumer {
    private final static String TOPIC = "delay-transfer-topic";
    private final static String GROUP_ID = "group1";

    private final DelayedTransferRepository delayedTransferRepository;

    @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
    public void receive(ConsumerRecord<String, MessageDto> record) {
        final String userId = record.key();
        final MessageDto message = record.value();
        log.info("Consumed message`s key: {}", userId);
        log.info("Consumed message`s value: {}", message.toString());

        delayedTransferRepository.findById(message.getId()).ifPresent(
            delayedTransferStatusDto -> {
                if ("pending".equals(delayedTransferStatusDto.getStatus())) {
                    log.info("Transfer is delayed");
                    delayedTransferStatusDto.setStatus("is_pending");
                    delayedTransferRepository.save(delayedTransferStatusDto);
                }
            }
        );
    }
}
