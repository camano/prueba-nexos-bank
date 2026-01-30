package com.bank.inc.prueba_nexos.domain.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transaction {

    private Long transactionId;
    private Card card;
    private BigDecimal amount;
    private LocalDateTime date;
    private boolean reversed;
}
