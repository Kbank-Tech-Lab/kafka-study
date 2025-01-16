package org.producer.scheduler;

import lombok.RequiredArgsConstructor;
import org.producer.dto.DelayedTransferRequest;
import org.producer.dto.MessageDto;
import org.producer.service.DelayedTransferService;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class DelayedTransferScheduler {

    private final KafkaTemplate<String, MessageDto> kafka;
    private final DelayedTransferService delayedTransferService;
    @Scheduled(fixedRate = 1 * 60 * 1000)// 1분 주기 실행 (ms단위)
    public void readRequestAndProduceEvent() {
        System.out.println("1분 주기로 실행되는지 확인\t" + LocalDateTime.now().toString());
        // DB로부터 지연이체요청 내역을 읽어들임
        List<DelayedTransferRequest> requests = delayedTransferService.readDelayedTransferRequestOrderBy();
        // kafka로 이벤트 발행
        for (DelayedTransferRequest request : requests) {
            /* kafka.send() 메서드 파라미터 세팅
            topic: delay-transfer-topic, key: 송금자계좌번호, partition: N/A, value: request객체

            질문1)
                key 파라미터에 null이 세팅(= request.getFromAcount()이 null인 경우)되면, kafka.send() 메서드는 어떻게 동작?
            질문2)
                key에 따라 자동으로 partition이 결정되는 것? 아니면 partition을 직접 지정해야는 것?
                만약 전자일 경우, 내부적으로 어떻게 partition이 결정되는지 확인 필요
                만약 후자일 경우, 어떤 기준으로 partition을 결정해야하는지 확인 필요
            */
            System.out.println("전송할 데이터: " + request.toString());
            CompletableFuture<SendResult<String, MessageDto>> send = kafka.send("delay-transfer-topic", request.getFromAccount(), MessageDto.of(request));
            send.whenComplete((result, ex) -> {
                if (ex != null) {
                    System.out.println("전송 실패: " + ex.getMessage());
                } else {
                    System.out.println("전송 성공: " + result.getProducerRecord().value());
                }
            });
        }
    }
}
