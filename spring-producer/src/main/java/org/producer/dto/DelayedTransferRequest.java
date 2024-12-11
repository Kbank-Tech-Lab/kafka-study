package org.producer.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DelayedTransferRequest {
    private String fromAccount;

    private String toBankCode;

    private String toAccount;

    private Long transferAmount;

    private Timestamp requestedAt;
}
