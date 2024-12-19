package org.channel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class DepositAccountDto {
    // 계좌정보
    private UUID depositAccountId;
    private String accountNumber;
    private Long balance;

    // 고객정보
    private UUID customerId;
    private String customerName;
}
