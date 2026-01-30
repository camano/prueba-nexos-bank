package com.bank.inc.prueba_nexos.infrastructure.adapter.postgres.mapper;

import com.bank.inc.prueba_nexos.domain.models.Card;
import com.bank.inc.prueba_nexos.domain.models.Transaction;
import com.bank.inc.prueba_nexos.infrastructure.adapter.postgres.entity.CardEntity;
import com.bank.inc.prueba_nexos.infrastructure.adapter.postgres.entity.TransactionEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class TransactionMapperTest {
    @Mock
    private CardMapper cardMapper;

    @InjectMocks
    private TransactionMapper transactionMapper;
    // clase donde está toEntityPurchase()

    @Test
    void shouldMapPurchaseToEntityCorrectly() {

        BigDecimal price = new BigDecimal("150");

        Card card = new Card();
        card.setCardId("1234567890123456");

        CardEntity cardEntity = new CardEntity();
        cardEntity.setCardId("1234567890123456");

        when(cardMapper.toEntity(card)).thenReturn(cardEntity);

        TransactionEntity entity = transactionMapper.toEntityPurchase(card, price);

        assertNotNull(entity);


        assertNull(entity.getTransactionId());

        assertNotNull(entity.getCardEntity());
        assertEquals("1234567890123456", entity.getCardEntity().getCardId());

        assertEquals(price, entity.getAmount());

        assertFalse(entity.isReversed());


        assertNotNull(entity.getTransactionDate());
        assertTrue(
                Duration.between(entity.getTransactionDate(), LocalDateTime.now()).toSeconds() < 2
        );
        verify(cardMapper).toEntity(card);
    }

    @Test
    void shouldMapDomainTransactionToEntityCorrectly() {

        Transaction transaction = new Transaction();
        transaction.setTransactionId(10L);
        transaction.setAmount(new BigDecimal("75"));
        transaction.setReversed(true);

        Card card = new Card();
        card.setCardId("1234567890123456");
        transaction.setCard(card);

        CardEntity cardEntity = new CardEntity();
        cardEntity.setCardId("1234567890123456");

        when(cardMapper.toEntity(card)).thenReturn(cardEntity);

        TransactionEntity entity = transactionMapper.toEntity(transaction);

        assertNotNull(entity);


        assertEquals(10L, entity.getTransactionId());

        assertNotNull(entity.getCardEntity());
        assertEquals("1234567890123456", entity.getCardEntity().getCardId());

        assertEquals(new BigDecimal("75"), entity.getAmount());


        assertTrue(entity.isReversed());

        assertNotNull(entity.getTransactionDate());
        assertTrue(
                Duration.between(entity.getTransactionDate(), LocalDateTime.now()).toSeconds() < 2
        );

        verify(cardMapper).toEntity(card);
    }

    @Test
    void shouldMapEntityToDomainCorrectly() {

        // ---------- Arrange ----------
        TransactionEntity entity = new TransactionEntity();
        entity.setTransactionId(20L);
        entity.setAmount(new BigDecimal("120"));
        entity.setTransactionDate(LocalDateTime.now());
        entity.setReversed(false);

        CardEntity cardEntity = new CardEntity();
        cardEntity.setCardId("1234567890123456");
        entity.setCardEntity(cardEntity);

        Card card = new Card();
        card.setCardId("1234567890123456");

        when(cardMapper.toDomain(cardEntity)).thenReturn(card);

        Transaction tx = transactionMapper.toDomain(entity);

        assertNotNull(tx);

        assertEquals(20L, tx.getTransactionId());

        assertNotNull(tx.getCard());
        assertEquals("1234567890123456", tx.getCard().getCardId());

        assertEquals(new BigDecimal("120"), tx.getAmount());

        assertEquals(entity.getTransactionDate(), tx.getDate());

        assertFalse(tx.isReversed());

                verify(cardMapper).toDomain(cardEntity);
    }
}