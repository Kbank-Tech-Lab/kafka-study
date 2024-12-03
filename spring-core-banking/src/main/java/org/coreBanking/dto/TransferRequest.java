package org.coreBanking.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransferRequest {
    @NotNull(message = "ID is required")
    private String id;

    @NotNull(message = "From Account is required")
    private String fromAccount;

    @NotNull(message = "To Bank Code is required")
    private String toBankCode;

    @NotNull(message = "To Account is required")
    private String toAccount;

    @DecimalMin(value = "0.0", message = "Transfer Amount must be greater than or equal to 0")
    private Long transferAmount;
}
