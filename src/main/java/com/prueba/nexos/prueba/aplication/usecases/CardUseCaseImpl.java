package com.prueba.nexos.prueba.aplication.usecases;

import com.prueba.nexos.prueba.domain.domain.card.gateways.CardRepository;
import com.prueba.nexos.prueba.domain.domain.status.gateways.EstadosRepository;
import com.prueba.nexos.prueba.domain.domain.status.request.Estado;
import com.prueba.nexos.prueba.domain.model.request.Card;
import com.prueba.nexos.prueba.domain.model.response.CardResponse;
import com.prueba.nexos.prueba.domain.usecase.card.CardUseCase;
import com.prueba.nexos.prueba.infrastructure.helper.excepciones.Excepctiones;
import com.prueba.nexos.prueba.infrastructure.helper.utils.Utilidades;
import com.prueba.nexos.prueba.infrastructure.controller.models.request.CardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CardUseCaseImpl implements CardUseCase {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private EstadosRepository estadosRepository;


    @Autowired
    private Utilidades utilities;


    @Override
    public CardResponse generarCard(String productoId) {
        LocalDate fechaCreacion = LocalDate.from(LocalDate.now());
        LocalDate fechaVencimiento= fechaCreacion.plusYears(3);
        Estado estado =estadosRepository.estados(5L);
        Card cardBodyGenerate= Card.builder()
                .cardId(productoId)
                .balance(0.0)
                .nombreTitular("Jonathan caamano")
                .fechaCreacion(fechaCreacion)
                .fechaVencimiento(fechaVencimiento)
                .estado(estado)
                .build();
        Card cardGenerated=cardRepository.generateCard(cardBody(cardBodyGenerate));

        return CardResponse.builder()
                .mensaje("Se Genero el numero de la tarjeta ")
                .card(cardGenerated)
                .build();
    }

    @Override
    public Optional<CardResponse> activeCard(String cardId) {
        Card cardActive = cardRepository.findByCardId(cardId);
        Estado estado=estadosRepository.estados(4L);
        cardActive.setEstado(estado);
        return cardRepository.activeCardOrBlockOrBalance(cardActive)
                .map(card -> CardResponse.builder()
                        .mensaje("Se Activo la tarjeta " + cardId)
                        .card(cardActive)
                        .build());

    }

    @Override
    public Optional<CardResponse> blockCard(String cardId) {
        Card cardBlock = cardRepository.findByCardId(cardId);
        Estado estado=estadosRepository.estados(6L);
        cardBlock.setEstado(estado);
        return cardRepository.activeCardOrBlockOrBalance(cardBlock)
                .map(card -> CardResponse.builder()
                        .mensaje("Se Bloqueo  la tarjeta " + cardId)
                        .card(cardBlock)
                        .build());
    }

    @Override
    public Optional<CardResponse> rechangeBalance(CardRequest card) {
        Card cardRechange = cardRepository.findByCardId(card.getCardId());
        cardRechange.setBalance(cardRechange.getBalance() + card.getBalance());
        if(cardRechange.getEstado().getEstadoId()==4){
            return cardRepository.activeCardOrBlockOrBalance(cardRechange)
                    .map(card1 -> CardResponse.builder()
                            .mensaje("Se recargo el saldo a " + cardRechange.getBalance())
                            .card(cardRechange)
                            .build());
        }else {
            throw new Excepctiones("La tarjeta se encuentra bloqueada o inactiva");
        }
    }

    @Override
    public Card checkBalance(String cardId) {
        return cardRepository.findByCardId(cardId);
    }


    public Card cardBody (Card card){

        return Card.builder()
                .cardId(card.getCardId().concat(utilities.generarNumeroAleatorio()))
                .fechaCreacion(card.getFechaCreacion())
                .fechaVencimiento(card.getFechaVencimiento())
                .nombreTitular("jonathan romero")
                .balance(card.getBalance())
                .estado(card.getEstado())
                .build();

    }
}
