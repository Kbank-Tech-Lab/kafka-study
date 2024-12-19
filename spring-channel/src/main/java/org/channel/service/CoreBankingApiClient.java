package org.channel.service;

import org.channel.domain.DelayedTransferRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class CoreBankingApiClient {
    private final RestClient restClient;
    // TO-DO: 환경변수
    private final String coreBankingUrl = "http://localhost:8300";

    public CoreBankingApiClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.baseUrl(coreBankingUrl).build();
    }

    public HttpStatusCode registerDelayedTransfer(DelayedTransferRequest request) {
        /*
        fromAccount
           :
        "000000000000"
        toAccount
        :
        "000000000001"
        toBankCode
        :
        "87"
        transferAmount
        :
        "300"
        */

        try {
            ResponseEntity<String> entity = restClient.post().uri("/api/delayed-transfer/request").body(request).retrieve().toEntity(String.class);
            return entity.getStatusCode();
        } catch (Exception e) {
            throw new RuntimeException("지연이체등록 실패 (네트워크 오류 혹은 core-banking 오류)", e);
        }
    }
}
