package org.channel.service;

import org.springframework.stereotype.Service;

@Service
public class TransferService {
    public Object registerDelayedTransfer(Object obj) {
        // 날짜 혹은 시간을 보내는게 맞을까?
        // fromAccount, toAccount, amount, transferDate, transferTime

        // 계정계에 지연이체등록 API 호출 (blocking? non-blocking?)

        // 지연이체등록 성공시 성공 메시지 리턴
        return "00";
    }
}
