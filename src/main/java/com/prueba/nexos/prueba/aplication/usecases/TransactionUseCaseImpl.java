package com.prueba.nexos.prueba.aplication.usecases;

import com.prueba.nexos.prueba.domain.domain.card.gateways.CardRepository;
import com.prueba.nexos.prueba.domain.domain.status.gateways.EstadosRepository;
import com.prueba.nexos.prueba.domain.domain.status.request.Estado;
import com.prueba.nexos.prueba.domain.domain.transaction.gateways.TransationRepository;
import com.prueba.nexos.prueba.domain.domain.transaction.request.Transaction;
import com.prueba.nexos.prueba.domain.domain.transaction.response.TransactionResponse;
import com.prueba.nexos.prueba.domain.model.request.Card;
import com.prueba.nexos.prueba.domain.usecase.transactions.TransactionUseCase;
import com.prueba.nexos.prueba.infrastructure.helper.excepciones.Excepctiones;
import com.prueba.nexos.prueba.infrastructure.helper.utils.Utilidades;
import com.prueba.nexos.prueba.infrastructure.controller.models.request.TransactionPurchaseRequest;
import com.prueba.nexos.prueba.infrastructure.controller.models.request.TransactionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionUseCaseImpl implements TransactionUseCase {

    @Autowired
    private TransationRepository transationRepository;

    @Autowired
    private EstadosRepository estadosRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private Utilidades utilidades;

    @Override
    public Transaction consultarTransacion(Long transacionId) {
        return transationRepository.consultTransaction(transacionId);
    }

    @Override
    public Optional<TransactionResponse> anularTransacion(TransactionRequest transactionRequest) {
        Transaction transaction = transationRepository.consultTransaction(transactionRequest.getTransactionId());
        Card card = cardRepository.findByCardId(transactionRequest.getCardId());
        LocalDateTime fechaHoraInicio = transaction.getFechaTransaction();
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        Estado estado=estadosRepository.estados(3L);
        Duration duracion = Duration.between(fechaHoraActual, fechaHoraInicio);
        if(duracion.toHours()>24){
        throw new Excepctiones("La transacion supera las 24 horas y no puede ser anulada la transacion");
        }else if(transaction.getEstado().getEstadoId()!=3){
            transaction.setEstadoTransaction(false);
            transaction.setEstado(estado);
            card.setBalance(card.getBalance() + transaction.getPrecio());
            cardRepository.activeCardOrBlockOrBalance(card);
            return transationRepository.anularTransaction(transaction)
                    .map(transaction1 -> TransactionResponse
                            .builder()
                            .mensaje("Se anulo la transacion ")
                            .transaction(transaction1)
                            .build());
        }
        else {
            throw new Excepctiones("La transacion ya se encuentra anulada");
        }
    }

    @Override
    public Optional<TransactionResponse> transacionCompra(TransactionPurchaseRequest transaction) {
        Card cardTransaction = cardRepository.findByCardId(transaction.getCardId());
        Estado estados=estadosRepository.estados(1L);
        LocalDateTime fechaCreacion = LocalDateTime.from(LocalDateTime.now());
        LocalDateTime fechaCardVencimiento=cardTransaction.getFechaVencimiento().atStartOfDay();
        Long transactionAsignada = Long.parseLong(utilidades.generarNumeroAleatorio());

        if (fechaCreacion.isBefore(fechaCardVencimiento)) {
            if (cardTransaction.getBalance() > 0) {
                if (cardTransaction.getEstado().getEstadoId()==4 ) {
                    if(transaction.getPrice() <= cardTransaction.getBalance()){
                        Double descontar= cardTransaction.getBalance() - transaction.getPrice();
                        cardTransaction.setBalance(descontar);
                        Transaction transactionCreada = Transaction
                                .builder()
                                .transactionId(transactionAsignada)
                                .estadoTransaction(true)
                                .fechaTransaction(fechaCreacion)
                                .precio(transaction.getPrice())
                                .card(cardTransaction)
                                .estado(estados)
                                .build();
                        cardRepository.activeCardOrBlockOrBalance(cardTransaction);
                        transationRepository.transactionCompra(transactionCreada);
                        return Optional.of(TransactionResponse
                                .builder()
                                .mensaje("La transacion fue creada con exito ")
                                .transaction(transactionCreada)
                                .build());
                    }else{
                        throw new Excepctiones("Saldo insuficiente para la transacion");
                    }

                } else {
                    throw new Excepctiones("La tarjeta esta bloqueada");
                }

            } else {
                throw new Excepctiones("El saldo no es suficiente");
            }

        } else if (fechaCreacion.isAfter(fechaCardVencimiento)) {
            throw new Excepctiones("la tarjeta esta vencida");
        }

        return Optional.empty();
    }
}
