package com.bank.inc.prueba_nexos.infrastructure.adapter.postgres.mapper;

import com.bank.inc.prueba_nexos.domain.enums.CardStatus;
import com.bank.inc.prueba_nexos.domain.enums.Currency;
import com.bank.inc.prueba_nexos.domain.models.Card;
import com.bank.inc.prueba_nexos.infrastructure.adapter.postgres.entity.CardEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Random;

@Component
public class CardMapper {


    public CardEntity toEntityAdd (String productId) {
        String cardNumber = generateCardNumber(productId);
        return CardEntity.builder()
                .cardId(cardNumber)
                .productId(productId)
                .firstName("Jonathan")
                .lastName("romero")
                .expirationDate(LocalDate.now().plusYears(3))
                .balance(BigDecimal.ZERO)
                .status(CardStatus.INACTIVE)
                .currency(Currency.USD)
                .createdAt(LocalDateTime.now())
                .build();
    }


    public CardEntity toEntity (Card card) {
        return CardEntity.builder()
                .cardId(card.getCardId())
                .productId(card.getProductId())
                .firstName(card.getFirstName())
                .lastName(card.getLastName())
                .expirationDate(card.getExpirationDate())
                .balance(card.getBalance())
                .status(card.getStatus())
                .currency(card.getCurrency())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public Card toDomain(CardEntity cardEntity) {

        return Card.builder()
                .cardId(cardEntity.getCardId())
                .productId(cardEntity.getProductId())
                .firstName(cardEntity.getFirstName())
                .lastName(cardEntity.getLastName())
                .expirationDate(cardEntity.getExpirationDate())
                .balance(cardEntity.getBalance())
                .status(cardEntity.getStatus())
                .currency(cardEntity.getCurrency())
                .build();
    }


    private String generateCardNumber(String productId) {
        String random = String.format("%010d", new Random().nextLong(1_000_000_0000L));
        return productId + random;
    }
}
