package org.channel.service;

import lombok.RequiredArgsConstructor;
import org.channel.dto.DelayedTransferRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ApiClient {
    private final RestClient restClient;
    // TO-DO: 환경변수
    private final String coreBankingUrl = "http://localhost:8200";

    public ApiClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder.baseUrl(coreBankingUrl).build();
    }

    public void registerDelayedTransfer(DelayedTransferRequest request) {
        restClient.post().uri("/api/v1/transfer/delayed").body(request).retrieve();
    }
}
