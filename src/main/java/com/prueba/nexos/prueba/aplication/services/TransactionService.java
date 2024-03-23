package com.prueba.nexos.prueba.aplication.services;

import com.prueba.nexos.prueba.domain.domain.transaction.request.Transaction;
import com.prueba.nexos.prueba.domain.domain.transaction.response.TransactionResponse;
import com.prueba.nexos.prueba.domain.usecase.transactions.TransactionUseCase;
import com.prueba.nexos.prueba.infrastructure.controller.models.request.TransactionPurchaseRequest;
import com.prueba.nexos.prueba.infrastructure.controller.models.request.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class TransactionService implements TransactionUseCase {

    @Autowired
    private TransactionUseCase transactionUseCase;
    @Override
    public Transaction consultarTransacion(Long transacionId) {
        return transactionUseCase.consultarTransacion(transacionId);
    }

    @Override
    public Optional<TransactionResponse> anularTransacion(TransactionRequest transactionRequest) {
        return transactionUseCase.anularTransacion(transactionRequest);
    }

    @Override
    public Optional<TransactionResponse> transacionCompra(TransactionPurchaseRequest transaction) {
        return transactionUseCase.transacionCompra(transaction);
    }
}
