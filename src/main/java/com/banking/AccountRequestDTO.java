package com.banking;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String accountNumber;

    @Min(0)
    private BigDecimal balance;

    @NotBlank
    private String accountType;

}
