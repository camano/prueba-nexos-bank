package com.prueba.nexos.prueba.infrastructure.controller.models.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionPurchaseRequest {

    private String cardId;
    private Double price;
}
