package org.channel.controller;

import lombok.RequiredArgsConstructor;
import org.channel.domain.DelayedTransferRequest;
import org.channel.domain.TransferLog;
import org.channel.dto.DepositAccountDto;
import org.channel.service.TransferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/transfer")
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;
    @GetMapping("/home")
    public String transferHome(Model model) {
        return "/TransferHome";
    }

    @GetMapping("/depositAccountDtoList")
    @ResponseBody
    public List<DepositAccountDto> getDepositAccountDtoList() {
        // 고객식별자, 고객계좌 식별자, 고객계좌 잔액
        return transferService.getDepositAccountDtoList();
    }
    // TO-DO: 페이징 처리
    @GetMapping("/accountHistory/{accountNumber}")
    @ResponseBody
    public List<TransferLog> accountTransferHistory(@PathVariable("accountNumber") String accountNumber) {
        // 계좌이체내역 조회
        return transferService.getTransferLogsByAccountId(accountNumber);
    }

    // sync, blocking 방식
    @PostMapping("/register/delayed_transfer")
    @ResponseBody
    public String delayedTransfer(DelayedTransferRequest request) {
        Object out = transferService.registerDelayedTransfer(request);
        return "00";
    }

    @GetMapping("/test")
    @ResponseBody
    public String test () {
        return "test completed";
    }
}
