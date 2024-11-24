package org.producer.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/produce")
@RequiredArgsConstructor
public class ProducerApi {
    private final StringRedisTemplate redis;
    private final KafkaTemplate<String, String> kafka;

    @GetMapping("/produce")
    public String produceToKafka() {
        // key값에 userId 넣어주는 이유
        // userId가 같으면 동일한 partition으로 보내기 위함, 동일 partition이면 동일 consumer가 받음
        // 정리하면, 동일 userId -> 동일 partition -> 동일 consumer
        kafka.send("delay-transfer-topic", "userId", "지연이체 이벤트 발생");
        return "produceToKafka";
    }
}