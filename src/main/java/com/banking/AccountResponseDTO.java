package com.banking;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountResponseDTO {


    private Long id;
    private String name;
    private String accountNumber;
    private BigDecimal balance;
    private String accountType;

}
