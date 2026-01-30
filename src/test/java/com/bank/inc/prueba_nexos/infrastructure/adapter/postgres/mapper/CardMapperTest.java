package com.bank.inc.prueba_nexos.infrastructure.adapter.postgres.mapper;

import com.bank.inc.prueba_nexos.domain.enums.CardStatus;
import com.bank.inc.prueba_nexos.domain.enums.Currency;
import com.bank.inc.prueba_nexos.domain.models.Card;
import com.bank.inc.prueba_nexos.infrastructure.adapter.postgres.entity.CardEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class CardMapperTest {

    @InjectMocks
    private CardMapper cardMapper;

    @Test
    void shouldCreateCardEntityCorrectlyFromProductId() {

        String productId = "654321";

        CardEntity entity = cardMapper.toEntityAdd(productId);

        assertNotNull(entity);

        assertNotNull(entity.getCardId());
        assertEquals(16, entity.getCardId().length());
        assertTrue(entity.getCardId().startsWith(productId)); // primeros 6 dígitos

        assertEquals(productId, entity.getProductId());

        assertEquals("Jonathan", entity.getFirstName());
        assertEquals("romero", entity.getLastName());

        LocalDate expectedExp = LocalDate.now().plusYears(3);
        assertEquals(expectedExp, entity.getExpirationDate());

        assertEquals(BigDecimal.ZERO, entity.getBalance());

        assertEquals(CardStatus.INACTIVE, entity.getStatus());

        assertEquals(Currency.USD, entity.getCurrency());

        assertNotNull(entity.getCreatedAt());
        assertTrue(
                Duration.between(entity.getCreatedAt(), LocalDateTime.now()).toSeconds() < 2
        );
    }


    @Test
    void shouldMapDomainCardToEntityCorrectly() {

        Card card = new Card();
        card.setCardId("1234567890123456");
        card.setProductId("654321");
        card.setFirstName("Jonathan");
        card.setLastName("Romero");
        card.setExpirationDate(LocalDate.now().plusYears(2));
        card.setBalance(new BigDecimal("300"));
        card.setStatus(CardStatus.ACTIVE);
        card.setCurrency(Currency.USD);

        CardEntity entity = cardMapper.toEntity(card);

        assertNotNull(entity);

        assertEquals(card.getCardId(), entity.getCardId());
        assertEquals(card.getProductId(), entity.getProductId());
        assertEquals(card.getFirstName(), entity.getFirstName());
        assertEquals(card.getLastName(), entity.getLastName());
        assertEquals(card.getExpirationDate(), entity.getExpirationDate());
        assertEquals(card.getBalance(), entity.getBalance());
        assertEquals(card.getStatus(), entity.getStatus());
        assertEquals(card.getCurrency(), entity.getCurrency());

        assertNotNull(entity.getCreatedAt());
        assertTrue(
                Duration.between(entity.getCreatedAt(), LocalDateTime.now()).toSeconds() < 2
        );
    }

    @Test
    void shouldMapEntityCardToDomainCorrectly() {

        CardEntity entity = new CardEntity();
        entity.setCardId("1234567890123456");
        entity.setProductId("654321");
        entity.setFirstName("Jonathan");
        entity.setLastName("Romero");
        entity.setExpirationDate(LocalDate.now().plusYears(1));
        entity.setBalance(new BigDecimal("500"));
        entity.setStatus(CardStatus.BLOCKED);
        entity.setCurrency(Currency.USD);

        Card card = cardMapper.toDomain(entity);

        assertNotNull(card);

        assertEquals(entity.getCardId(), card.getCardId());
        assertEquals(entity.getProductId(), card.getProductId());
        assertEquals(entity.getFirstName(), card.getFirstName());
        assertEquals(entity.getLastName(), card.getLastName());
        assertEquals(entity.getExpirationDate(), card.getExpirationDate());
        assertEquals(entity.getBalance(), card.getBalance());
        assertEquals(entity.getStatus(), card.getStatus());
        assertEquals(entity.getCurrency(), card.getCurrency());
    }
}