package org.coreBanking.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DelayedTransferRequestDTO {

    @NotBlank(message = "From Account is required and cannot be blank")
    private String fromAccount;

    @NotBlank(message = "To Bank Code is required and cannot be blank")
    private String toBankCode;

    @NotBlank(message = "To Account is required and cannot be blank")
    private String toAccount;

    @NotNull(message = "Transfer Amount is required")
    @DecimalMin(value = "1", message = "Transfer Amount must be greater than 0")
    private Long transferAmount;
}
