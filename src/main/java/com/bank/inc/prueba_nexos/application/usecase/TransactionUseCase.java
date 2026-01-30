package com.bank.inc.prueba_nexos.application.usecase;

import com.bank.inc.prueba_nexos.domain.Rules.DomainRules;
import com.bank.inc.prueba_nexos.domain.models.Card;
import com.bank.inc.prueba_nexos.domain.models.Transaction;
import com.bank.inc.prueba_nexos.domain.port.CardRepositoryPort;
import com.bank.inc.prueba_nexos.domain.port.TransactionRepositoryPort;
import com.bank.inc.prueba_nexos.infrastructure.web.request.TransactionAnulationRequest;
import com.bank.inc.prueba_nexos.infrastructure.web.request.TransactionRequest;
import org.springframework.stereotype.Service;

@Service
public class TransactionUseCase {

    private final TransactionRepositoryPort transactionRepositoryPort;
    private final CardRepositoryPort cardRepositoryPort;

    public TransactionUseCase(TransactionRepositoryPort transactionRepositoryPort, CardRepositoryPort cardRepositoryPort) {
        this.transactionRepositoryPort = transactionRepositoryPort;
        this.cardRepositoryPort = cardRepositoryPort;
    }

    public Transaction purchase(TransactionRequest transactionRequest){

        Card card = cardRepositoryPort.findByCardId(transactionRequest.cardId());
        DomainRules.validatePurchase(card,transactionRequest.price());

        card.setBalance(card.getBalance().subtract(transactionRequest.price()));
        cardRepositoryPort.activate(card);

        return transactionRepositoryPort.create(card,transactionRequest.price());
    }

    public Transaction findByTransation(Long transacionId) {
        return transactionRepositoryPort.findById(transacionId);
    }

    public Transaction reverseTransaction(TransactionAnulationRequest transaction){
        Transaction tx = transactionRepositoryPort.findById(transaction.transactionId());
        DomainRules.validateReverse(tx);
        Card card = cardRepositoryPort.findByCardId(transaction.cardId());
        card.setBalance(card.getBalance().add(tx.getAmount()));
        tx.setReversed(true);
        cardRepositoryPort.activate(card);
        return transactionRepositoryPort.reverse(tx);
    }
}
