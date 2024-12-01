package org.consumer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class MessageDto {
    private Long id;

    @JsonProperty("from_account")
    private String fromAccount;

    @JsonProperty("to_bank_code")
    private String toBankCode;

    @JsonProperty("to_account")
    private String toAccount;

    @JsonProperty("transfer_amount")
    private Long transferAmount;

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
