package com.prueba.nexos.prueba.domain.domain.card.gateways;

import com.prueba.nexos.prueba.domain.model.request.Card;

import java.util.Optional;

public interface CardRepository {


    Card generateCard(Card card);

    Card findByCardId(String cardId);

    Optional<Card> activeCardOrBlockOrBalance(Card card);




}
