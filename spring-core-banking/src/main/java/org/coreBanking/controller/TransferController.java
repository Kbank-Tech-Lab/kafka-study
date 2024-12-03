package org.coreBanking.controller;

import org.coreBanking.dto.TransferRequest;
import org.coreBanking.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transfer")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/process")
    public ResponseEntity<String> processTransfer(@RequestBody TransferRequest transferRequest) {

        transferService.processTransfer(transferRequest);

        return ResponseEntity.ok("Transfer processed successfully.");
    }
}
