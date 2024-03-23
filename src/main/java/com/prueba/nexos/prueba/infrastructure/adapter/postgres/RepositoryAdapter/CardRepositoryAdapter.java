package com.prueba.nexos.prueba.infrastructure.adapter.postgres.RepositoryAdapter;

import com.prueba.nexos.prueba.domain.domain.card.gateways.CardRepository;
import com.prueba.nexos.prueba.domain.model.request.Card;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.entity.CardEntitity;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.mapper.RepositotyMapper;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.repository.CardEntityRepository;
import com.prueba.nexos.prueba.infrastructure.helper.excepciones.Excepctiones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CardRepositoryAdapter implements CardRepository {


    @Autowired
    private final CardEntityRepository cardEntityRepository;

    @Autowired
    private final RepositotyMapper repositotyMapper;

    public CardRepositoryAdapter(CardEntityRepository cardEntityRepository, RepositotyMapper repositotyMapper) {
        this.cardEntityRepository = cardEntityRepository;
        this.repositotyMapper = repositotyMapper;
    }

    @Override
    public Card generateCard(Card card) {
        try {
            CardEntitity cardEntititySave = cardEntityRepository.save(repositotyMapper.mapCard(card));
            return repositotyMapper.mapEntityCard(cardEntititySave);
        } catch (DataAccessException e) {
            throw new Excepctiones("Hubo un error al general la tarjeta " +e.getMessage());
        }


    }

    @Override
    public Card findByCardId(String cardId) {
        return cardEntityRepository.findById(cardId).map(cardEntitity -> repositotyMapper.mapEntityCard(cardEntitity))
                .orElseThrow(() -> new Excepctiones("No se encontro el cardId"));
    }

    @Override
    public Optional<Card> activeCardOrBlockOrBalance(Card card) {
        try {
            CardEntitity cardUpdate = cardEntityRepository.save(repositotyMapper.mapCard(card));
            return Optional.of(repositotyMapper.mapEntityCard(cardUpdate));
        }catch (DataAccessException e){
            throw new Excepctiones("Hubo un error en el proceso ");
        }

    }


}
