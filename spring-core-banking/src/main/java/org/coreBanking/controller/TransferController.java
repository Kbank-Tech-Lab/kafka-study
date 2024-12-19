package org.coreBanking.controller;


import jakarta.validation.Valid;
import org.coreBanking.dto.TransferRequestDTO;
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

    // 송금 API
    @PostMapping("/process")
    public ResponseEntity<String> processTransfer(@Valid @RequestBody TransferRequestDTO transferRequestDTO) {

        transferService.processTransfer(transferRequestDTO);

        return ResponseEntity.ok("Transfer processed successfully.");
    }
}
