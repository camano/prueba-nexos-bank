package com.bank.inc.prueba_nexos.infrastructure.adapter.postgres.entity;

import com.bank.inc.prueba_nexos.domain.enums.CardStatus;
import com.bank.inc.prueba_nexos.domain.enums.Currency;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "card")
public class CardEntity {
    @Id
    private String cardId; // 16 dígitos

    private String productId; // primeros 6 dígitos

    private String firstName;
    private String lastName;

    private LocalDate expirationDate;

    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    private CardStatus status;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private LocalDateTime createdAt;
}
