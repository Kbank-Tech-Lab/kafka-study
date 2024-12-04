package org.consumer.util;

import org.consumer.dto.MessageDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ExternalApiClient {
    private final RestClient restClient;
    private final static String BASE_URL = "http://localhost:8300/api/transfer";

    public ExternalApiClient() {
        this.restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .defaultHeader(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     * Core-Banking의 이체API를 호출하는 메서드
     *
     * @param messageDto     요청에 포함할 데이터
     * @param successAction  성공 상태(200~299)일 때 실행할 람다식
     * @param failureAction  실패 상태(500~599)일 때 실행할 람다식
     */
    public void callTransferApi(MessageDto messageDto,
                                Runnable successAction,
                                Runnable failureAction) {
        restClient.post()
                .uri("/transfer")
                .body(messageDto)
                .exchange((request, response) -> {
                    if (response.getStatusCode().is2xxSuccessful()) {
                        successAction.run();
                        return null;
                    }

                    if (response.getStatusCode().is5xxServerError()) {
                        failureAction.run();
                    }
                    return null;
                });
    }

}
