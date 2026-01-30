package com.bank.inc.prueba_nexos.domain.Rules;

import com.bank.inc.prueba_nexos.domain.enums.CardStatus;
import com.bank.inc.prueba_nexos.domain.enums.Currency;
import com.bank.inc.prueba_nexos.domain.models.Card;
import com.bank.inc.prueba_nexos.infrastructure.helper.excepciones.BusinessException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DomainRulesTest {

    @Test
    void shouldFailWhenBalanceIsInsufficient() {

        Card card = new Card();
        card.setStatus(CardStatus.ACTIVE);
        card.setBalance(new BigDecimal("10"));
        card.setCurrency(Currency.USD);
        card.setExpirationDate(LocalDate.now().plusDays(10));

        BigDecimal amount = new BigDecimal("50");

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> DomainRules.validatePurchase(card, amount)
        );

        assertEquals("Saldo insuficiente", ex.getMessage());
    }

    @Test
    void shouldFailWhenCardIsNotActive() {

        Card card = baseValidCard();
        card.setStatus(CardStatus.INACTIVE);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> DomainRules.validatePurchase(card, new BigDecimal("10"))
        );

        assertEquals("Tarjeta no activa", ex.getMessage());
    }

    // -------- Tarjeta bloqueada --------
    @Test
    void shouldFailWhenCardIsBlocked() {

        Card card = baseValidCard();
        card.setStatus(CardStatus.INACTIVE);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> DomainRules.validatePurchase(card, new BigDecimal("10"))
        );


        assertEquals("Tarjeta no activa", ex.getMessage());
    }

    // -------- Tarjeta vencida --------
    @Test
    void shouldFailWhenCardIsExpired() {

        Card card = baseValidCard();
        card.setExpirationDate(LocalDate.now().minusDays(1));

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> DomainRules.validatePurchase(card, new BigDecimal("10"))
        );

        assertEquals("Tarjeta vencida", ex.getMessage());
    }


    // -------- Moneda inválida --------
    @Test
    void shouldFailWhenCurrencyIsNotUSD() {

        Card card = baseValidCard();
        card.setCurrency(Currency.EUR); // o cualquier otra

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> DomainRules.validatePurchase(card, new BigDecimal("10"))
        );

        assertEquals("Solo USD permitido", ex.getMessage());
    }

    // -------- Factory base válida --------
    private Card baseValidCard(){
        Card card = new Card();
        card.setStatus(CardStatus.ACTIVE);
        card.setCurrency(Currency.USD);
        card.setExpirationDate(LocalDate.now().plusDays(10));
        card.setBalance(new BigDecimal("100"));
        return card;
    }
}