package org.consumer.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/consumer")
@RequiredArgsConstructor
public class ConsumerApi {
    private final StringRedisTemplate redis;

    @KafkaListener(topics = "delay-transfer-topic", groupId = "group1")
    public String consumerFromKafka(String event) {
        System.out.println("이벤트 메시지: " + event);
        return "consumerFromKafka";
    }
}