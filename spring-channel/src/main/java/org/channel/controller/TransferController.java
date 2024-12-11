package org.channel.controller;

import lombok.RequiredArgsConstructor;
import org.channel.dto.Customer;
import org.channel.dto.DelayedTransferRequest;
import org.channel.dto.TransferLog;
import org.channel.repository.TransferLogRepository;
import org.channel.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller("transfer")
public class TransferController {
    private final TransferService transferService;
    private final RestClient restClient;
    public TransferController(TransferService transferService, RestClient.Builder restClientBuilder ) {
        this.transferService = transferService;
        this.restClient = restClientBuilder.build();
    }
    @GetMapping("/home")
    public String transferHome(Model model) {
        // 고객식별자, 고객계좌 식별자, 고객계좌 잔액
        List<Object> userList = new ArrayList<>();
        model.addAttribute("userList", userList);
        return "TransferHome";
    }

    // TO-DO: 페이징 처리
    @GetMapping("/accountHistory/{accountId}/{curPage}/{pageCount}")
    @ResponseBody
    public List<TransferLog> accountTransferHistory(@PathVariable("accountId") UUID accountId) {
        // 계좌이체내역 조회
        return transferService.getTransferLogsByAccountId(accountId);
    }

    // sync, blocking 방식
    @PostMapping("/register/delayed_transfer")
    @ResponseBody
    public String delayedTransfer(DelayedTransferRequest request) {
        Object out = transferService.registerDelayedTransfer(request);
        restClient.post().uri("/delayed-transfer").body(request).retrieve();
        return "00";
    }
}
