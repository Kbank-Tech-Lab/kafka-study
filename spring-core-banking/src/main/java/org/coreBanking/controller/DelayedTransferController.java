package org.coreBanking.controller;

import jakarta.validation.Valid;
import org.coreBanking.dto.DelayedTransferRequestDTO;
import org.coreBanking.model.DelayedTransferRequest;
import org.coreBanking.service.DelayedTransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/delayed-transfer")
public class DelayedTransferController {

    private final DelayedTransferService delayedTransferService;

    public DelayedTransferController(DelayedTransferService delayedTransferService) {
        this.delayedTransferService = delayedTransferService;
    }

    // 지연 이체 신청 API
    @PostMapping("/request")
    public ResponseEntity<DelayedTransferRequest> createDelayedTransferRequest(@Valid @RequestBody DelayedTransferRequestDTO requestDTO) {

        DelayedTransferRequest delayedTransferRequest = delayedTransferService.createDelayedTransferRequest(requestDTO);

        return new ResponseEntity<>(delayedTransferRequest, HttpStatus.CREATED);
    }
}
