package org.coreBanking.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/coreBanking")
@RequiredArgsConstructor
public class CoreBankingApi {
    private final StringRedisTemplate redis;
    private final KafkaTemplate<String, String> kafka;

    @GetMapping("/ImmediatelyTransfer")
    public String ImmediatelyTransfer() {
        return "ImmediatelyTransfer";
    }
}