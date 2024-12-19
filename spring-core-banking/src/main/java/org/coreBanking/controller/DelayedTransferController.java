package org.coreBanking.controller;

import org.coreBanking.dto.DelayedTransferRequestDTO;
import org.coreBanking.model.DelayedTransferRequest;
import org.coreBanking.service.DelayedTransferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/delayed-transfer")
public class DelayedTransferController {

    private final DelayedTransferService delayedTransferService;

    public DelayedTransferController(DelayedTransferService delayedTransferService) {
        this.delayedTransferService = delayedTransferService;
    }

    // 지연 이체 신청 API
    @PostMapping("/request")
    public ResponseEntity<DelayedTransferRequest> createDelayedTransferRequest(@RequestBody DelayedTransferRequestDTO requestDTO) {

        DelayedTransferRequest delayedTransferRequest = delayedTransferService.createDelayedTransferRequest(requestDTO);

        return new ResponseEntity<>(delayedTransferRequest, HttpStatus.CREATED);
    }
}
