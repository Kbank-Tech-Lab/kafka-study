package org.consumer.dto;

import lombok.Data;

@Data
public class MessageDto {
    private Integer delayedTransferId;
    private String fromAccount;
    private String toBankCode;
    private String toAccount;
    private Long transferAmount;

    @Override
    public String toString() {
        return "MessageDto{" +
                "delayedTransferId='" + delayedTransferId + '\'' +
                ", fromAccount='" + fromAccount + '\'' +
                ", toBankCode='" + toBankCode + '\'' +
                ", toAccount='" + toAccount + '\'' +
                ", transferAmount=" + transferAmount +
                '}';
    }
}
