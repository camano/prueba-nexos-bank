package com.bank.inc.prueba_nexos.domain.models;

import com.bank.inc.prueba_nexos.domain.enums.CardStatus;
import com.bank.inc.prueba_nexos.domain.enums.Currency;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Card {
    private String cardId;
    private String productId;
    private String firstName;
    private String lastName;
    private LocalDate expirationDate;
    private BigDecimal balance;
    private CardStatus status;
    private Currency currency;
}
