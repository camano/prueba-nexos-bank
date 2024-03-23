package com.prueba.nexos.prueba.domain.usecase.card;

import com.prueba.nexos.prueba.domain.model.request.Card;
import com.prueba.nexos.prueba.domain.model.response.CardResponse;
import com.prueba.nexos.prueba.infrastructure.controller.models.request.CardRequest;

import java.util.Optional;


public interface CardUseCase {


    CardResponse generarCard(String productoId);


    Optional<CardResponse> activeCard(String cardId);

    Optional<CardResponse> blockCard(String cardId);

    Optional<CardResponse> rechangeBalance(CardRequest card);

    Card checkBalance (String cardId);



}
