package org.producer.scheduler;

import lombok.RequiredArgsConstructor;
import org.producer.dto.DelayedTransferRequest;
import org.producer.dto.MessageDto;
import org.producer.service.DelayedTransferService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DelayedTransferScheduler {

    private final KafkaTemplate<String, MessageDto> kafka;
    private final DelayedTransferService delayedTransferService;
    @Scheduled(fixedRate = 1 * 60 * 1000)// 1분 주기 실행
    public void readRequestAndProduceEvent() {
        // DB로부터 지연이체요청 내역을 읽어들임
        List<DelayedTransferRequest> requests = delayedTransferService.readDelayedTransferRequestOrderBy();
        // kafa로 이벤트 발행
        for (DelayedTransferRequest request : requests) {
            kafka.send("delay-transfer-topic", "depositAccount 값 넣어주기", MessageDto.of(request));
        }
    }
}
