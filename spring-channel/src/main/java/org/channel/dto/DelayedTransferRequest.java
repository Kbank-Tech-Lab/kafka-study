package org.channel.dto;

import java.sql.Timestamp;

import lombok.*;

@Data
public class DelayedTransferRequest {
    private String fromAccount;

    private String toBankCode;

    private String toAccount;

    private Long transferAmount;

    private Timestamp requestedAt;
}
