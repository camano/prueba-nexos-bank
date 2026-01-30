package com.bank.inc.prueba_nexos.application.usecase;

import com.bank.inc.prueba_nexos.domain.Rules.DomainRules;
import com.bank.inc.prueba_nexos.domain.enums.CardStatus;
import com.bank.inc.prueba_nexos.domain.models.Card;
import com.bank.inc.prueba_nexos.domain.port.CardRepositoryPort;
import com.bank.inc.prueba_nexos.infrastructure.web.request.CardRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;

@Service
public class CardUseCase {

    private final CardRepositoryPort cardRepositoryPort;

    public CardUseCase(CardRepositoryPort cardRepositoryPort) {
        this.cardRepositoryPort = cardRepositoryPort;
    }

    public Card issueCard(String productoId){
        return cardRepositoryPort.generateCard(productoId);
    }


    public Card activateCard(CardRequest cardRequest){
        Card card = cardRepositoryPort.findByCardId(cardRequest.cardId());
       DomainRules.validateActivation(card);
        card.setStatus(CardStatus.ACTIVE);
        return cardRepositoryPort.activate(card);
    }

    public Card blockCard(String cardId){
        Card card = cardRepositoryPort.findByCardId(cardId);
        DomainRules.validateBlock(card);
        card.setStatus(CardStatus.BLOCKED);
        return cardRepositoryPort.activate(card);
    }

    public Card recharge(String cardId, BigDecimal monto){
        Card card = cardRepositoryPort.findByCardId(cardId);
        DomainRules.validateRecharge(card);
        card.setBalance(card.getBalance().add(monto));
        return cardRepositoryPort.activate(card);
    }

    public Card checkBalance(String cardId) {
        return cardRepositoryPort.findByCardId(cardId);
    }
}
