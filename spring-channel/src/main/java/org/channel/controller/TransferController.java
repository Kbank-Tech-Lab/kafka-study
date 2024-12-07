package org.channel.controller;

import lombok.RequiredArgsConstructor;
import org.channel.service.TransferService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller("transfer")
@RequiredArgsConstructor
public class TransferController {
    private final TransferService transferService;
    @GetMapping("/home")
    public String transferHome(Model model) {
        // 고객식별자, 고객계좌 식별자, 고객계좌 잔액
        List<Object> userList = new ArrayList<>();
        model.addAttribute("userList", userList);
        return "TransferHome";
    }

    @GetMapping("/userList/{curPage}/{pageCount}")
    @ResponseBody
    public List<Object> userList(@PathVariable("curPage") Integer curPage, @PathVariable("pageCount") Integer pageCount) {
        return new ArrayList<>();
    }

    @GetMapping("/accountHistory/{accountId}/{curPage}/{pageCount}")
    @ResponseBody
    public List<Object> accountTransferHistory(@PathVariable("accountId") String accountId, @PathVariable("curPage") Integer curPage, @PathVariable("pageCount") Integer pageCount) {
        // 계좌이체내역 조회
        return new ArrayList<>();
    }

    @PostMapping("/register/delayed_transfer")
    @ResponseBody
    public String delayedTransfer(Object obj) {
        // 날짜 혹은 시간을 보내는게 맞을까?
        // fromAccount, toAccount, amount, transferDate, transferTime
        Object out = transferService.registerDelayedTransfer(obj);
        // 지연이체등록 성공시 성공 메시지 리턴
        return "00";
    }
}
