package com.prueba.nexos.prueba.domain.usecase.card;

import com.prueba.nexos.prueba.aplication.usecases.CardUseCaseImpl;
import com.prueba.nexos.prueba.domain.domain.card.gateways.CardRepository;
import com.prueba.nexos.prueba.domain.domain.status.gateways.EstadosRepository;
import com.prueba.nexos.prueba.domain.domain.status.request.Estado;
import com.prueba.nexos.prueba.domain.model.request.Card;
import com.prueba.nexos.prueba.domain.model.response.CardResponse;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.entity.CardEntitity;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.entity.EstadoEntity;
import com.prueba.nexos.prueba.infrastructure.controller.models.request.CardRequest;
import com.prueba.nexos.prueba.infrastructure.helper.utils.Utilidades;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CardUseCaseTest {

    @Mock
    private CardRepository cardRepository;
    @Mock
    private EstadosRepository estadosRepository;
    @Mock
    private Utilidades utilities;

    @InjectMocks
    private CardUseCaseImpl cardUseCase;

    private Card card;
    private Estado estado;
    private String cardId;
    private CardEntitity cardEntitity;
    private EstadoEntity estadoEntity;



    @BeforeEach
    void setUp() {
        cardId="1234562187075088";
        estado = Estado.builder().nombreEstado("Aprobada").estadoId(1L).build();
        estadoEntity = EstadoEntity.builder().nombreEstado(estado.getNombreEstado()).estadoId(estado.getEstadoId()).build();
        card = Card.builder()
                .estado(estado)
                .fechaVencimiento(LocalDate.now().plusYears(3))
                .fechaCreacion(LocalDate.now())
                .cardId(cardId)
                .nombreTitular("Jonathan romero")
                .balance(0.0)
                .build();

        cardEntitity = CardEntitity.builder()
                .estadoId(estadoEntity)
                .fechaVencimiento(card.getFechaVencimiento())
                .fechaCreacion(card.getFechaCreacion())
                .cardId(card.getCardId())
                .nombreTitular(card.getNombreTitular())
                .balance(card.getBalance())
                .build();
    }

    @Test
    void generarCard() {
        when(cardRepository.generateCard(card)).thenReturn(card);

        CardResponse cardResponse= cardUseCase.generarCard("123456");

        assertEquals(cardId,cardResponse.getCard().getCardId());
    }

    @Test
    void activeCard() {
        when(cardRepository.findByCardId(cardId)).thenReturn(card);
        when(estadosRepository.estados(card.getEstado().getEstadoId())).thenReturn(estado);
        when(cardRepository.activeCardOrBlockOrBalance(card)).thenReturn(Optional.of(card));

        Optional<CardResponse>cardActive=cardUseCase.activeCard(cardId);

        assertEquals("Se Activo la tarjeta 1234562187075088",cardActive.get().getMensaje());

    }

    @Test
    void blockCard() {

        when(cardRepository.findByCardId(cardId)).thenReturn(card);
        when(estadosRepository.estados(card.getEstado().getEstadoId())).thenReturn(estado);
        when(cardRepository.activeCardOrBlockOrBalance(card)).thenReturn(Optional.of(card));

        Optional<CardResponse>cardActive=cardUseCase.blockCard(cardId);

        assertEquals("Se Bloqueo  la tarjeta 1234562187075088",cardActive.get().getMensaje());
    }

    @Test
    void rechangeBalance() {
        CardRequest cardRequest=CardRequest.builder()
                .balance(5000.0)
                .cardId(cardId)
                .build();

        when(cardRepository.findByCardId(cardId)).thenReturn(card);
        when(cardRepository.activeCardOrBlockOrBalance(card)).thenReturn(Optional.of(card));

        Optional<CardResponse>cardActive=cardUseCase.rechangeBalance(cardRequest);

        assertEquals("Se recargo el saldo a 5000.0",cardActive.get().getMensaje());
    }

    @Test
    void checkBalance() {
        when(cardRepository.findByCardId(cardId)).thenReturn(card);
        Card cardObtenido = cardUseCase.checkBalance(cardId);
        assertEquals(cardId,cardObtenido.getCardId());
    }
}