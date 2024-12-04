package org.consumer.dto;

import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.Getter;

@Getter
public class MessageDto {
    private Long id;
    private String fromAccount;
    private String toBankCode;
    private String toAccount;
    private Long transferAmount;

    @JsonSetter("id")
    public void setId(Long id) {
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
        return "DelayedTransferDto{" +
                "id='" + id + '\'' +
                ", fromAccount='" + fromAccount + '\'' +
                ", toBankCode='" + toBankCode + '\'' +
                ", toAccount='" + toAccount + '\'' +
                ", transferAmount=" + transferAmount +
                '}';
    }
}
