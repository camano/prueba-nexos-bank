package com.bank.inc.prueba_nexos.infrastructure.adapter.postgres.adapter;

import com.bank.inc.prueba_nexos.domain.models.Card;
import com.bank.inc.prueba_nexos.domain.models.Transaction;
import com.bank.inc.prueba_nexos.domain.port.TransactionRepositoryPort;
import com.bank.inc.prueba_nexos.infrastructure.adapter.postgres.mapper.TransactionMapper;
import com.bank.inc.prueba_nexos.infrastructure.adapter.postgres.repository.TransactionRepository;
import com.bank.inc.prueba_nexos.infrastructure.helper.excepciones.BusinessException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransactionRepositoryAdapter implements TransactionRepositoryPort {


    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    public TransactionRepositoryAdapter(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
        this.transactionRepository = transactionRepository;
        this.transactionMapper = transactionMapper;
    }

    @Override
    public Transaction create(Card card, BigDecimal price) {
        return transactionMapper.toDomain(transactionRepository.save(transactionMapper.toEntityPurchase(card, price)));
    }

    @Override
    public Transaction findById(Long transactionId) {
        return transactionMapper.toDomain(transactionRepository.findById(transactionId)
                .orElseThrow(()->new BusinessException("NO Se encontro la transaccion ")));
    }

    @Override
    public Transaction reverse(Transaction transaction) {

        return transactionMapper.toDomain(transactionRepository.save(transactionMapper.toEntity(transaction)));
    }
}
