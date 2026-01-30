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

        // ---------- Arrange ----------
        String productId = "654321";

        // ---------- Act ----------
        CardEntity entity = cardMapper.toEntityAdd(productId);

        // ---------- Assert ----------
        assertNotNull(entity);

        // card number
        assertNotNull(entity.getCardId());
        assertEquals(16, entity.getCardId().length());
        assertTrue(entity.getCardId().startsWith(productId)); // primeros 6 dígitos

        // product
        assertEquals(productId, entity.getProductId());

        // holder
        assertEquals("Jonathan", entity.getFirstName());
        assertEquals("romero", entity.getLastName());

        // expiration (3 años)
        LocalDate expectedExp = LocalDate.now().plusYears(3);
        assertEquals(expectedExp, entity.getExpirationDate());

        // balance
        assertEquals(BigDecimal.ZERO, entity.getBalance());

        // status
        assertEquals(CardStatus.INACTIVE, entity.getStatus());

        // currency
        assertEquals(Currency.USD, entity.getCurrency());

        // createdAt
        assertNotNull(entity.getCreatedAt());
        assertTrue(
                Duration.between(entity.getCreatedAt(), LocalDateTime.now()).toSeconds() < 2
        );
    }


    @Test
    void shouldMapDomainCardToEntityCorrectly() {

        // ---------- Arrange ----------
        Card card = new Card();
        card.setCardId("1234567890123456");
        card.setProductId("654321");
        card.setFirstName("Jonathan");
        card.setLastName("Romero");
        card.setExpirationDate(LocalDate.now().plusYears(2));
        card.setBalance(new BigDecimal("300"));
        card.setStatus(CardStatus.ACTIVE);
        card.setCurrency(Currency.USD);

        // ---------- Act ----------
        CardEntity entity = cardMapper.toEntity(card);

        // ---------- Assert ----------
        assertNotNull(entity);

        // campos directos
        assertEquals(card.getCardId(), entity.getCardId());
        assertEquals(card.getProductId(), entity.getProductId());
        assertEquals(card.getFirstName(), entity.getFirstName());
        assertEquals(card.getLastName(), entity.getLastName());
        assertEquals(card.getExpirationDate(), entity.getExpirationDate());
        assertEquals(card.getBalance(), entity.getBalance());
        assertEquals(card.getStatus(), entity.getStatus());
        assertEquals(card.getCurrency(), entity.getCurrency());

        // createdAt automático
        assertNotNull(entity.getCreatedAt());
        assertTrue(
                Duration.between(entity.getCreatedAt(), LocalDateTime.now()).toSeconds() < 2
        );
    }

    @Test
    void shouldMapEntityCardToDomainCorrectly() {

        // ---------- Arrange ----------
        CardEntity entity = new CardEntity();
        entity.setCardId("1234567890123456");
        entity.setProductId("654321");
        entity.setFirstName("Jonathan");
        entity.setLastName("Romero");
        entity.setExpirationDate(LocalDate.now().plusYears(1));
        entity.setBalance(new BigDecimal("500"));
        entity.setStatus(CardStatus.BLOCKED);
        entity.setCurrency(Currency.USD);

        // ---------- Act ----------
        Card card = cardMapper.toDomain(entity);

        // ---------- Assert ----------
        assertNotNull(card);

        // campos directos
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