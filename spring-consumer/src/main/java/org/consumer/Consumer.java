package org.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Consumer {
    private final static String TOPIC = "delay-transfer-topic";
    private final static String GROUP_ID = "group1";

    @KafkaListener(topics = TOPIC, groupId = GROUP_ID)
    public void receive(ConsumerRecord<String, DelayedTransferDto> record) {
        final String userId = record.key();
        final DelayedTransferDto message = record.value();
        log.info("Consumed message`s key: {}", userId);
        log.info("Consumed message`s key: {}", message.toString());
    }
}
