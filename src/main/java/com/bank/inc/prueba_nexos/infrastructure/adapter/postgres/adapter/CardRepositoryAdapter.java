package com.bank.inc.prueba_nexos.infrastructure.adapter.postgres.adapter;

import com.bank.inc.prueba_nexos.domain.models.Card;
import com.bank.inc.prueba_nexos.domain.port.CardRepositoryPort;
import com.bank.inc.prueba_nexos.infrastructure.adapter.postgres.mapper.CardMapper;
import com.bank.inc.prueba_nexos.infrastructure.adapter.postgres.repository.CardRepository;

import com.bank.inc.prueba_nexos.infrastructure.helper.excepciones.BusinessException;
import org.springframework.stereotype.Component;


import java.util.Optional;

@Component
public class CardRepositoryAdapter implements CardRepositoryPort {


    private final CardRepository  cardRepository;
    private final CardMapper cardMapper;

    public CardRepositoryAdapter(CardRepository cardRepository, CardMapper cardMapper) {
        this.cardRepository = cardRepository;
        this.cardMapper = cardMapper;
    }


    @Override
    public Card generateCard(String productoId) {
        return cardMapper.toDomain(cardRepository.save(cardMapper.toEntityAdd(productoId)));

    }

    @Override
    public Card findByCardId(String cardId) {
        return cardRepository.findById(cardId)
                .map(cardMapper::toDomain)
                .orElseThrow(()->new BusinessException("NO Se encontro la tarjeta"));

    }

    @Override
    public Card activate(Card card) {
        return cardMapper.toDomain(cardRepository.save(cardMapper.toEntity(card)));
    }




}
