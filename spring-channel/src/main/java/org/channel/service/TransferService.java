package org.channel.service;

import org.channel.dto.Customer;
import org.channel.dto.DelayedTransferRequest;
import org.channel.dto.TransferLog;
import org.channel.repository.CustomerRepository;
import org.channel.repository.TransferLogRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.UUID;

@Service
public class TransferService {
    private final RestClient restClient;
    // TO-DO: 환경변수
    private final String producerUrl = "http://localhost:8200";
    private final TransferLogRepository transferLogRepository = null;
    private final CustomerRepository customerRepository = null;
    public TransferService(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.baseUrl(producerUrl).build();
    }

    public List<TransferLog> getTransferLogsByFromAccountId(UUID accountId, Integer curPage, Integer pageCount) {
        // 계좌이체내역 조회
        return transferLogRepository.findTransferLogsByFromAccountId(accountId, PageRequest.of(curPage, pageCount));
    }

    public List<TransferLog> getTransferLogsByToAccountId(UUID accountId, Integer curPage, Integer pageCount) {
        // 계좌이체내역 조회
        return transferLogRepository.findTransferLogsByToAccountId(accountId, PageRequest.of(curPage, pageCount));
    }

    public List<TransferLog> getTransferLogsByAccountId(UUID accountId) {
        // 계좌이체내역 조회
        return transferLogRepository.findTransferLogsByAccountId(accountId);
    }

    public String registerDelayedTransfer(DelayedTransferRequest request) {
        // producer 서버에 지연이체 요청
        restClient.post().uri("/delayed-transfer").body(request).retrieve();
        // 지연이체등록 성공시 성공 메시지 리턴
        return "00";
    }
}
