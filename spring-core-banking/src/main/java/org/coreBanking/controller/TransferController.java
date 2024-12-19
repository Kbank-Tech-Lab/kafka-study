package org.coreBanking.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.coreBanking.service.TransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseEntity<String> processTransfer(HttpServletRequest request) {

        // 요청 파라미터
        System.out.println("Parameters:");
        request.getParameterMap().forEach((key, value) -> {
            System.out.println(key + ": " + String.join(", ", value));
        });

        // 요청 바디
        try {
            String body = request.getReader().lines().reduce("", (accumulator, actual) -> accumulator + actual);
            System.out.println("Body: " + body);
        } catch (Exception e) {
            System.out.println("Failed to read body: " + e.getMessage());
        }

//        transferService.processTransfer(transferRequestDTO);

        return ResponseEntity.ok("Transfer processed successfully.");
    }

    // 송금 API
//    @PostMapping("/process")
//    public ResponseEntity<String> processTransfer(@Valid @RequestBody TransferRequestDTO transferRequestDTO) {
//
//        transferService.processTransfer(transferRequestDTO);
//
//        return ResponseEntity.ok("Transfer processed successfully.");
//    }
}
