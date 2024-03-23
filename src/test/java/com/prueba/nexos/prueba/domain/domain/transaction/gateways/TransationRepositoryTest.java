package com.prueba.nexos.prueba.domain.domain.transaction.gateways;

import com.prueba.nexos.prueba.domain.domain.status.request.Estado;
import com.prueba.nexos.prueba.domain.domain.transaction.request.Transaction;
import com.prueba.nexos.prueba.domain.model.request.Card;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.RepositoryAdapter.TrasactionRepositoryAdapter;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.entity.CardEntitity;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.entity.EstadoEntity;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.entity.TransactionEntity;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.mapper.RepositotyMapper;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.repository.TransactionEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransationRepositoryTest {

    @Mock
    private TransactionEntityRepository transationRepository;

    @Mock
    private RepositotyMapper repositotyMapper;

    @InjectMocks
    private TrasactionRepositoryAdapter trasactionRepositoryAdapter;

    private TransactionEntity transactionEntity;
    private Transaction transaction;
    private EstadoEntity estadoEntity;
    private Estado estado;
    private CardEntitity cardEntitity;
    private Card card;

    private Long transactionId;

    @BeforeEach
    void setUp() {
        transactionId=1L;
        estado= Estado.builder().estadoId(1L).nombreEstado("Aprobada").build();
        card = Card.builder()
                .estado(estado)
                .fechaVencimiento(LocalDate.now().plusYears(3))
                .fechaCreacion(LocalDate.now())
                .cardId("1234567")
                .nombreTitular("Jonathan romero")
                .balance(0.0)
                .build();
        transaction =Transaction.builder()
                .precio(0.0)
                .card(card)
                .estadoTransaction(true)
                .transactionId(1L)
                .estado(estado)
                .fechaTransaction(LocalDateTime.now())
                .build();
        estadoEntity= EstadoEntity.builder().estadoId(estado.getEstadoId()).nombreEstado(estado.getNombreEstado()).build();
        cardEntitity= CardEntitity.builder()
                .estadoId(estadoEntity)
                .fechaVencimiento(card.getFechaVencimiento())
                .fechaCreacion(card.getFechaCreacion())
                .cardId(card.getCardId())
                .nombreTitular(card.getNombreTitular())
                .balance(card.getBalance())
                .build();
        transactionEntity=TransactionEntity.builder()
                .precio(transaction.getPrecio())
                .cardId(cardEntitity)
                .estadoTransaction(transaction.isEstadoTransaction())
                .transactionId(transaction.getTransactionId())
                .estadoId(estadoEntity)
                .fechaTransaction(LocalDateTime.now())
                .build();
    }

    @Test
    void transactionCompra() {
        when(repositotyMapper.mapTransaction(any(Transaction.class))).thenReturn(transactionEntity);
        when(transationRepository.save(transactionEntity)).thenReturn(transactionEntity);
        when(repositotyMapper.mapEntityTransaction(any(TransactionEntity.class))).thenReturn(transaction);

        Transaction transactionSave=trasactionRepositoryAdapter.transactionCompra(transaction);

        assertEquals(transactionId,transactionSave.getTransactionId());
    }

    @Test
    void consultTransaction() {
        when(transationRepository.findById(transactionId)).thenReturn(Optional.of(transactionEntity));
        when(repositotyMapper.mapEntityTransaction(any(TransactionEntity.class))).thenReturn(transaction);

        Transaction transactionObtenido=trasactionRepositoryAdapter.consultTransaction(transactionId);


        assertEquals(transactionId,transactionObtenido.getTransactionId());
    }

    @Test
    void anularTransaction() {
        when(repositotyMapper.mapTransaction(any(Transaction.class))).thenReturn(transactionEntity);
        when(transationRepository.save(transactionEntity)).thenReturn(transactionEntity);
        when(repositotyMapper.mapEntityTransaction(any(TransactionEntity.class))).thenReturn(transaction);

        Transaction transactionSave=trasactionRepositoryAdapter.transactionCompra(transaction);

        assertEquals(transactionId,transactionSave.getTransactionId());
    }
}