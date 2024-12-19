package org.producer.dto;

import lombok.Data;

@Data
public class MessageDto {
    private Integer delayedTransferId;
    private String fromAccount;
    private String toBankCode;
    private String toAccount;
    private Long transferAmount;

    public static MessageDto of(DelayedTransferRequest request) {
        MessageDto messageDto = new MessageDto();
        messageDto.delayedTransferId = request.getId();
        messageDto.fromAccount = request.getFromAccount();
        messageDto.toBankCode = request.getToBankCode();
        messageDto.toAccount = request.getToAccount();
        messageDto.transferAmount = request.getTransferAmount();

        return messageDto;
    }

//    @Override
//    public String toString() {
//        return "MessageDto{" +
//                "delayedTransferId='" + delayedTransferId + '\'' +
//                ", fromAccount='" + fromAccount + '\'' +
//                ", toBankCode='" + toBankCode + '\'' +
//                ", toAccount='" + toAccount + '\'' +
//                ", transferAmount=" + transferAmount +
//                '}';
//    }
}
