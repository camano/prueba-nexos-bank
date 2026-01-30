package com.bank.inc.prueba_nexos.infrastructure.adapter.postgres.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "transaction")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionId;

    @ManyToOne
    private CardEntity cardEntity;

    private BigDecimal amount;

    private LocalDateTime transactionDate;

    private boolean reversed;
}
