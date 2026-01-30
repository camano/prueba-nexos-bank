package com.bank.inc.prueba_nexos.domain.port;

import com.bank.inc.prueba_nexos.domain.models.Card;
import com.bank.inc.prueba_nexos.domain.models.Transaction;

import java.math.BigDecimal;

public interface TransactionRepositoryPort {

    Transaction create (Card card, BigDecimal price);

    Transaction findById(Long transactionId);

    Transaction reverse(Transaction transaction);
}
