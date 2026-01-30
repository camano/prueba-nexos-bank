package com.bank.inc.prueba_nexos.domain.Rules;

import com.bank.inc.prueba_nexos.domain.enums.CardStatus;
import com.bank.inc.prueba_nexos.domain.enums.Currency;
import com.bank.inc.prueba_nexos.domain.models.Card;
import com.bank.inc.prueba_nexos.domain.models.Transaction;
import com.bank.inc.prueba_nexos.infrastructure.helper.excepciones.BusinessException;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DomainRules {

    public static void validatePurchase(Card card, BigDecimal amount){

        if(card.getStatus() != CardStatus.ACTIVE)
            throw new BusinessException("Tarjeta no activa");

        if(card.getStatus() == CardStatus.BLOCKED)
            throw new BusinessException("Tarjeta bloqueada");

        if(LocalDate.now().isAfter(card.getExpirationDate()))
            throw new BusinessException("Tarjeta vencida");

        if(card.getBalance().compareTo(amount) < 0)
            throw new BusinessException("Saldo insuficiente");

        if(card.getCurrency() != Currency.USD)
            throw new BusinessException("Solo USD permitido");
    }

    public static void validateActivation(Card card){
        if(card.getStatus() != CardStatus.INACTIVE)
            throw new BusinessException("La tarjeta no está inactiva");
    }

    public static void validateBlock(Card card){
        if(card.getStatus() != CardStatus.ACTIVE){
            throw new BusinessException("La tarjeta no puede ser bloqueada");
        }
    }

    public static void validateRecharge(Card card){
        if(card.getStatus() != CardStatus.ACTIVE)
            throw new BusinessException("Tarjeta no activa");
    }

    public static void validateReverse(Transaction tx){
        if(tx.isReversed())
            throw new BusinessException("Ya anulada");

        long hours = Duration.between(tx.getDate(), LocalDateTime.now()).toHours();
        if(hours > 24)
            throw new BusinessException("La transaccion supero las  24 horas");
    }
}
