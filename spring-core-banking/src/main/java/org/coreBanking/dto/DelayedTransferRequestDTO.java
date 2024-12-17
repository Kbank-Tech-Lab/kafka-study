package org.coreBanking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DelayedTransferRequestDTO {

    private String fromAccount;
    private String toBankCode;
    private String toAccount;
    private Long transferAmount;
}
