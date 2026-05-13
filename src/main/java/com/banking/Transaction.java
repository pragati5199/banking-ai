package com.banking;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transId;

    private String senderAccountNumber;
    private String receiverAccountNumber;
    private BigDecimal transAmount;
    private LocalDateTime transTimeStamp;

    @Enumerated(EnumType.STRING)
    private TransactionType transType;

    @Enumerated(EnumType.STRING)
    private TransactionStatus transStatus;


}
