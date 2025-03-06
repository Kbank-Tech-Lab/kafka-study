package org.producer.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Data;
import lombok.Getter;

@Data
public class MessageDto {
    private Integer id;
    private String fromAccount;
    private String toBankCode;
    private String toAccount;
    private Long transferAmount;

    public static MessageDto of(DelayedTransferRequest request) {
        MessageDto messageDto = new MessageDto();
        messageDto.id = request.getId();
        messageDto.fromAccount = request.getFromAccount();
        messageDto.toBankCode = request.getToBankCode();
        messageDto.toAccount = request.getToAccount();
        messageDto.transferAmount = request.getTransferAmount();

        return messageDto;
    }

    @JsonSetter("id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonSetter("from_account")
    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount;
    }

    @JsonSetter("to_bank_code")
    public void setToBankCode(String toBankCode) {
        this.toBankCode = toBankCode;
    }

    @JsonSetter("to_account")
    public void setToAccount(String toAccount) {
        this.toAccount = toAccount;
    }

    @JsonSetter("transfer_amount")
    public void setTransferAmount(Long transferAmount) {
        this.transferAmount = transferAmount;
    }

    @Override
    public String toString() {
        return "MessageDto{" +
                "id='" + id + '\'' +
                ", fromAccount='" + fromAccount + '\'' +
                ", toBankCode='" + toBankCode + '\'' +
                ", toAccount='" + toAccount + '\'' +
                ", transferAmount=" + transferAmount +
                '}';
    }
}
