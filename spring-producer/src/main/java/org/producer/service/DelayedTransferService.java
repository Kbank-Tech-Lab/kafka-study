package org.producer.service;

import lombok.RequiredArgsConstructor;
import org.producer.dto.DelayedTransferRequest;
import org.producer.repository.DelayedTransferRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DelayedTransferService {

    private final DelayedTransferRequestRepository delayedTransferRequestRepository;

    /**
     * 지연이체요청 테이블을 조회한다
     *  WHERE status = 'PENDING' <- TO-DO: status가 'FAIL'인 경우도 조회해야하는지 확인
     *  ORDER BY requested_at ASC <- TO-DO: 컨슈머가 kafka message queue에서 ASC 순서로 읽는지, DESC 순서로 읽는지 확인
     */
    public List<DelayedTransferRequest> readDelayedTransferRequestOrderBy() {
        return delayedTransferRequestRepository.findByStatusOrderByRequestedAtAsc("PENDING");
    }
}
