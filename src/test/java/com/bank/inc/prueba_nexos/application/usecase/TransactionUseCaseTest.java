package com.bank.inc.prueba_nexos.application.usecase;

import com.bank.inc.prueba_nexos.domain.Rules.DomainRules;
import com.bank.inc.prueba_nexos.domain.enums.CardStatus;
import com.bank.inc.prueba_nexos.domain.enums.Currency;
import com.bank.inc.prueba_nexos.domain.models.Card;
import com.bank.inc.prueba_nexos.domain.models.Transaction;
import com.bank.inc.prueba_nexos.domain.port.CardRepositoryPort;
import com.bank.inc.prueba_nexos.domain.port.TransactionRepositoryPort;
import com.bank.inc.prueba_nexos.infrastructure.helper.excepciones.BusinessException;
import com.bank.inc.prueba_nexos.infrastructure.web.request.TransactionAnulationRequest;
import com.bank.inc.prueba_nexos.infrastructure.web.request.TransactionRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionUseCaseTest {

    @Mock
    private CardRepositoryPort cardRepositoryPort;

    @Mock
    private TransactionRepositoryPort transactionRepositoryPort;

    @InjectMocks
    private TransactionUseCase transactionUseCase;

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
    void shouldMakePurchaseSuccessfully() {

        String cardId = "1234567890123456";
        BigDecimal price = new BigDecimal("20");

        TransactionRequest request = new TransactionRequest(cardId, price);

        Card card = new Card();
        card.setCardId(cardId);
        card.setStatus(CardStatus.ACTIVE);
        card.setCurrency(Currency.USD);
        card.setExpirationDate(LocalDate.now().plusDays(10));
        card.setBalance(new BigDecimal("100"));

        Transaction tx = new Transaction();
        tx.setTransactionId(1L);
        tx.setAmount(price);
        tx.setCard(card);

        when(cardRepositoryPort.findByCardId(cardId)).thenReturn(card);
        when(transactionRepositoryPort.create(any(Card.class), eq(price)))
                .thenReturn(tx);

        Transaction result = transactionUseCase.purchase(request);


        assertNotNull(result);
        assertEquals(1L, result.getTransactionId());
        assertEquals(price, result.getAmount());

        assertEquals(new BigDecimal("80"), card.getBalance());

        verify(cardRepositoryPort).findByCardId(cardId);
        verify(cardRepositoryPort).activate(card);
        verify(transactionRepositoryPort).create(card, price);
    }



    @Test
    void shouldFindTransactionByIdSuccessfully() {

        Long transactionId = 1L;

        Transaction tx = new Transaction();
        tx.setTransactionId(transactionId);
        tx.setAmount(new BigDecimal("50"));

        when(transactionRepositoryPort.findById(transactionId))
                .thenReturn(tx);

        Transaction result = transactionUseCase.findByTransation(transactionId);

        assertNotNull(result);
        assertEquals(transactionId, result.getTransactionId());
        assertEquals(new BigDecimal("50"), result.getAmount());

        verify(transactionRepositoryPort).findById(transactionId);
    }

    @Test
    void shouldReverseTransactionSuccessfully() {

        Long txId = 1L;
        String cardId = "1234567890123456";
        BigDecimal amount = new BigDecimal("50");

        TransactionAnulationRequest request =
                new TransactionAnulationRequest(cardId, txId);

        Transaction tx = new Transaction();
        tx.setTransactionId(txId);
        tx.setAmount(amount);
        tx.setReversed(false);
        tx.setDate(LocalDateTime.now().minusHours(2)); // < 24h

        Card card = new Card();
        card.setCardId(cardId);
        card.setStatus(CardStatus.ACTIVE);
        card.setCurrency(Currency.USD);
        card.setExpirationDate(LocalDate.now().plusDays(10));
        card.setBalance(new BigDecimal("100"));

        when(transactionRepositoryPort.findById(txId)).thenReturn(tx);
        when(cardRepositoryPort.findByCardId(cardId)).thenReturn(card);
        when(transactionRepositoryPort.reverse(any(Transaction.class)))
                .thenAnswer(i -> i.getArgument(0));

        Transaction result = transactionUseCase.reverseTransaction(request);

        assertNotNull(result);
        assertTrue(result.isReversed());

        assertEquals(new BigDecimal("150"), card.getBalance());

        verify(transactionRepositoryPort).findById(txId);
        verify(cardRepositoryPort).findByCardId(cardId);
        verify(cardRepositoryPort).activate(card);
        verify(transactionRepositoryPort).reverse(tx);
    }

    @Test
    void shouldReturnNullWhenTransactionNotFound(){

        Long transactionId = 99L;

        when(transactionRepositoryPort.findById(transactionId))
                .thenReturn(null);

        Transaction result = transactionUseCase.findByTransation(transactionId);

        assertNull(result);
    }

    @Test
    void shouldFailWhenTransactionAlreadyReversed(){

        Long txId = 1L;
        String cardId = "123";

        TransactionAnulationRequest request =
                new TransactionAnulationRequest(cardId, txId);

        Transaction tx = new Transaction();
        tx.setTransactionId(txId);
        tx.setReversed(true); // ya anulada
        tx.setDate(LocalDateTime.now().minusHours(1));

        when(transactionRepositoryPort.findById(txId)).thenReturn(tx);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> transactionUseCase.reverseTransaction(request)
        );

        assertEquals("Ya anulada", ex.getMessage());

        verify(transactionRepositoryPort, never()).reverse(any());
    }

    @Test
    void shouldFailWhenTransactionIsOlderThan24Hours(){

        Long txId = 1L;
        String cardId = "123";

        TransactionAnulationRequest request =
                new TransactionAnulationRequest(cardId, txId);

        Transaction tx = new Transaction();
        tx.setTransactionId(txId);
        tx.setReversed(false);
        tx.setDate(LocalDateTime.now().minusHours(30)); // > 24h

        when(transactionRepositoryPort.findById(txId)).thenReturn(tx);

        BusinessException ex = assertThrows(
                BusinessException.class,
                () -> transactionUseCase.reverseTransaction(request)
        );

        assertEquals("La transaccion supero las  24 horas", ex.getMessage());

        verify(transactionRepositoryPort, never()).reverse(any());
    }
}
