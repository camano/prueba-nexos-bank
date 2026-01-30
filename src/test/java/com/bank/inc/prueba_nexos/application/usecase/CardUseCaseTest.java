package com.bank.inc.prueba_nexos.application.usecase;

import com.bank.inc.prueba_nexos.domain.enums.CardStatus;
import com.bank.inc.prueba_nexos.domain.enums.Currency;
import com.bank.inc.prueba_nexos.domain.models.Card;
import com.bank.inc.prueba_nexos.domain.port.CardRepositoryPort;
import com.bank.inc.prueba_nexos.infrastructure.helper.excepciones.BusinessException;
import com.bank.inc.prueba_nexos.infrastructure.web.request.CardRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardUseCaseTest {

    @Mock
    CardRepositoryPort cardRepositoryPort;

    @InjectMocks
    CardUseCase cardUseCase;

    @Test
    void shouldIssueCardSuccessfully() {

        String productId = "123456";

        Card card = new Card();
        card.setCardId("1234567890123456");
        card.setProductId(productId);
        card.setStatus(CardStatus.INACTIVE);
        card.setBalance(BigDecimal.ZERO);
        card.setCurrency(Currency.USD);
        card.setExpirationDate(LocalDate.now().plusYears(3));

        when(cardRepositoryPort.generateCard(productId))
                .thenReturn(card);

        Card result = cardUseCase.issueCard(productId);

        assertNotNull(result);
        assertEquals(productId, result.getProductId());
        assertEquals("1234567890123456", result.getCardId());
        assertEquals(CardStatus.INACTIVE, result.getStatus());
        assertEquals(BigDecimal.ZERO, result.getBalance());
        assertEquals(Currency.USD, result.getCurrency());

        verify(cardRepositoryPort).generateCard(productId);
    }

    @Test
    void shouldActivateCardSuccessfully() {

        String cardId = "1234567890123456";
        BigDecimal balance = new BigDecimal("20");

        CardRequest cardRequest = new CardRequest(cardId,balance);

        Card card = new Card();
        card.setCardId(cardId);
        card.setStatus(CardStatus.INACTIVE);
        card.setBalance(BigDecimal.ZERO);
        card.setCurrency(Currency.USD);
        card.setExpirationDate(LocalDate.now().plusYears(3));

        when(cardRepositoryPort.findByCardId(cardId))
                .thenReturn(card);

        when(cardRepositoryPort.activate(any(Card.class)))
                .thenAnswer(i -> i.getArgument(0));

        Card result = cardUseCase.activateCard(cardRequest);

        assertNotNull(result);
        assertEquals(CardStatus.ACTIVE, result.getStatus());
        assertEquals(cardId, result.getCardId());

        verify(cardRepositoryPort).findByCardId(cardId);
        verify(cardRepositoryPort).activate(card);
    }

    @Test
    void shouldFailWhenRepositoryThrowsException(){

        String productId = "123456";

        when(cardRepositoryPort.generateCard(productId))
                .thenThrow(new BusinessException("Error generando tarjeta"));

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> cardUseCase.issueCard(productId)
        );

        assertEquals("Error generando tarjeta", ex.getMessage());
    }

    @Test
    void shouldFailWhenCardIsNotInactive(){

        String cardId = "123";
CardRequest cardRequest = new CardRequest(cardId,BigDecimal.ZERO);
        Card card = new Card();
        card.setCardId(cardId);
        card.setStatus(CardStatus.BLOCKED); // inválido
        card.setBalance(BigDecimal.ZERO);

        when(cardRepositoryPort.findByCardId(cardId))
                .thenReturn(card);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> cardUseCase.activateCard(cardRequest)
        );

        assertEquals("La tarjeta no está inactiva", ex.getMessage());

        verify(cardRepositoryPort, never()).activate(any());
    }

    @Test
    void shouldBlockCardSuccessfully() {

        // ---------- Arrange ----------
        String cardId = "1234567890123456";

        Card card = new Card();
        card.setCardId(cardId);
        card.setStatus(CardStatus.ACTIVE); // estado válido para bloqueo
        card.setBalance(BigDecimal.ZERO);
        card.setCurrency(Currency.USD);
        card.setExpirationDate(LocalDate.now().plusYears(3));

        when(cardRepositoryPort.findByCardId(cardId))
                .thenReturn(card);

        when(cardRepositoryPort.activate(any(Card.class)))
                .thenAnswer(i -> i.getArgument(0));

        // ---------- Act ----------
        Card result = cardUseCase.blockCard(cardId);

        // ---------- Assert ----------
        assertNotNull(result);
        assertEquals(CardStatus.BLOCKED, result.getStatus());
        assertEquals(cardId, result.getCardId());

        // Verificaciones
        verify(cardRepositoryPort).findByCardId(cardId);
        verify(cardRepositoryPort).activate(card);
    }

    @Test
    void shouldRechargeCardSuccessfully() {


        String cardId = "1234567890123456";
        BigDecimal monto = new BigDecimal("50");

        Card card = new Card();
        card.setCardId(cardId);
        card.setStatus(CardStatus.ACTIVE); // requisito para recarga
        card.setBalance(new BigDecimal("100"));
        card.setCurrency(Currency.USD);
        card.setExpirationDate(LocalDate.now().plusYears(3));

        when(cardRepositoryPort.findByCardId(cardId))
                .thenReturn(card);

        when(cardRepositoryPort.activate(any(Card.class)))
                .thenAnswer(i -> i.getArgument(0));

        Card result = cardUseCase.recharge(cardId, monto);

        assertNotNull(result);
        assertEquals(new BigDecimal("150"), result.getBalance());
        assertEquals(CardStatus.ACTIVE, result.getStatus());
        assertEquals(cardId, result.getCardId());

        verify(cardRepositoryPort).findByCardId(cardId);
        verify(cardRepositoryPort).activate(card);
    }
    @Test
    void shouldFailWhenCardIsNotActive(){

        String cardId = "123";

        Card card = new Card();
        card.setCardId(cardId);
        card.setStatus(CardStatus.BLOCKED); // inválido para recarga
        card.setBalance(new BigDecimal("100"));

        when(cardRepositoryPort.findByCardId(cardId))
                .thenReturn(card);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> cardUseCase.recharge(cardId, new BigDecimal("50"))
        );

        assertEquals("Tarjeta no activa", ex.getMessage());

        verify(cardRepositoryPort, never()).activate(any());
    }

    @Test
    void shouldReturnCardBalanceSuccessfully() {

        String cardId = "1234567890123456";

        Card card = new Card();
        card.setCardId(cardId);
        card.setBalance(new BigDecimal("250"));
        card.setStatus(CardStatus.ACTIVE);
        card.setCurrency(Currency.USD);

        when(cardRepositoryPort.findByCardId(cardId))
                .thenReturn(card);

        Card result = cardUseCase.checkBalance(cardId);

        assertNotNull(result);
        assertEquals(cardId, result.getCardId());
        assertEquals(new BigDecimal("250"), result.getBalance());
        assertEquals(CardStatus.ACTIVE, result.getStatus());

        verify(cardRepositoryPort).findByCardId(cardId);
    }

    @Test
    void shouldReturnNullWhenCardNotFound(){

        when(cardRepositoryPort.findByCardId("999"))
                .thenReturn(null);

        Card result = cardUseCase.checkBalance("999");

        assertNull(result);
    }

    @Test
    void shouldFailWhenCardCannotBeBlocked(){

        String cardId = "123";

        Card card = new Card();
        card.setCardId(cardId);
        card.setStatus(CardStatus.INACTIVE); // inválido para bloqueo

        when(cardRepositoryPort.findByCardId(cardId))
                .thenReturn(card);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> cardUseCase.blockCard(cardId)
        );

        assertEquals("La tarjeta no puede ser bloqueada", ex.getMessage());

        verify(cardRepositoryPort, never()).activate(any());
    }
}