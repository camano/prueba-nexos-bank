package com.bank.inc.prueba_nexos.domain.port;

import com.bank.inc.prueba_nexos.domain.models.Card;

import java.util.Optional;

public interface CardRepositoryPort {
    Card generateCard(String productoId);
    Card findByCardId(String cardId);

    Card activate(Card card);

}
