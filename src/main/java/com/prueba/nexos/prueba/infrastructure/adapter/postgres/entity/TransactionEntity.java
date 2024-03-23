package com.prueba.nexos.prueba.infrastructure.adapter.postgres.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Transaction")
public class TransactionEntity {

    @Id
    @Column(name = "transactionId")
    private Long transactionId;

    @Column(name = "precio")
    private Double precio;

    @Column(name = "fechaTransacion")
    private LocalDateTime fechaTransaction;

    @Column(name = "transactionEstado")
    private  boolean estadoTransaction;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cardIdTransaction",nullable = false)
    private CardEntitity cardId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estadoIdTransaction",nullable = false)
    private EstadoEntity estadoId;

}
