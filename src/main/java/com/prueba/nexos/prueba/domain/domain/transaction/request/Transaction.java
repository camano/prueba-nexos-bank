package com.prueba.nexos.prueba.domain.domain.transaction.request;

import com.prueba.nexos.prueba.domain.domain.status.request.Estado;
import com.prueba.nexos.prueba.domain.model.request.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {

    private Long transactionId;

    private LocalDateTime fechaTransaction;

    private Double precio;

    private boolean estadoTransaction;

    private Card card;

    private Estado estado;
}
