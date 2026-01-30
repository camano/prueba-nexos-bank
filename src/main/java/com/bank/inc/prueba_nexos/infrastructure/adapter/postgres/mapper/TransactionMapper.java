package com.bank.inc.prueba_nexos.infrastructure.adapter.postgres.mapper;

import com.bank.inc.prueba_nexos.domain.models.Card;
import com.bank.inc.prueba_nexos.domain.models.Transaction;
import com.bank.inc.prueba_nexos.infrastructure.adapter.postgres.entity.TransactionEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class TransactionMapper {

    private final CardMapper cardMapper;

    public TransactionMapper(CardMapper cardMapper) {
        this.cardMapper = cardMapper;
    }

    public TransactionEntity toEntityPurchase(Card card, BigDecimal price){

        return TransactionEntity.builder()
                .transactionId(null)
                .cardEntity(cardMapper.toEntity(card))
                .amount(price)
                .transactionDate(LocalDateTime.now())
                .reversed(false)
                .build();
    }

    public TransactionEntity toEntity(Transaction transaction){

        return TransactionEntity.builder()
                .transactionId(transaction.getTransactionId())
                .cardEntity(cardMapper.toEntity(transaction.getCard()))
                .amount(transaction.getAmount())
                .transactionDate(LocalDateTime.now())
                .reversed(transaction.isReversed())
                .build();
    }


    public Transaction toDomain(TransactionEntity transaction){
        return Transaction.builder()
                .transactionId(transaction.getTransactionId())
                .card(cardMapper.toDomain(transaction.getCardEntity()))
                .amount(transaction.getAmount())
                .date(transaction.getTransactionDate())
                .reversed(transaction.isReversed())
                .build();
    }
}
