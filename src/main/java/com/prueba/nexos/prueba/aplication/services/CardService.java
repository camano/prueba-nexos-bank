package com.prueba.nexos.prueba.aplication.services;

import com.prueba.nexos.prueba.domain.model.request.Card;
import com.prueba.nexos.prueba.domain.model.response.CardResponse;
import com.prueba.nexos.prueba.domain.usecase.card.CardUseCase;
import com.prueba.nexos.prueba.infrastructure.controller.models.request.CardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardService implements CardUseCase {

    @Autowired
    private CardUseCase cardUseCase;

    @Override
    public CardResponse generarCard(String productoId) {
        return cardUseCase.generarCard(productoId);
    }

    @Override
    public Optional<CardResponse> activeCard(String cardId) {
        return cardUseCase.activeCard(cardId);
    }

    @Override
    public Optional<CardResponse> blockCard(String cardId) {
        return cardUseCase.blockCard(cardId);
    }

    @Override
    public Optional<CardResponse> rechangeBalance(CardRequest card) {
        return cardUseCase.rechangeBalance(card);
    }

    @Override
    public Card checkBalance(String cardId) {
        return cardUseCase.checkBalance(cardId);
    }
}
