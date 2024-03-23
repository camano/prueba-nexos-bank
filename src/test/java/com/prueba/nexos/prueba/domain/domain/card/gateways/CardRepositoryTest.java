package com.prueba.nexos.prueba.domain.domain.card.gateways;

import com.prueba.nexos.prueba.domain.domain.status.request.Estado;
import com.prueba.nexos.prueba.domain.model.request.Card;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.RepositoryAdapter.CardRepositoryAdapter;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.entity.CardEntitity;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.entity.EstadoEntity;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.mapper.RepositotyMapper;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.repository.CardEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class CardRepositoryTest {

    @Mock
    private CardEntityRepository cardEntityRepository;

    @Mock
    private RepositotyMapper repositotyMapper;

    @InjectMocks
    private CardRepositoryAdapter cardRepositoryAdapter;

    private String cardId;
    private EstadoEntity estado;
    private CardEntitity cardEntitity;
    private Estado estados;
    private Card cardDto;

    @BeforeEach
    void setUp() {
        cardId = "1234567674555246";
        estado = EstadoEntity.builder().nombreEstado("Aprobada").estadoId(1L).build();
        estados = Estado.builder().nombreEstado(estado.getNombreEstado()).estadoId(estado.getEstadoId()).build();

        cardDto = Card.builder()
                .estado(estados)
                .fechaVencimiento(LocalDate.now().plusYears(3))
                .fechaCreacion(LocalDate.now())
                .cardId(cardId)
                .nombreTitular("Jonathan romero")
                .balance(0.0)
                .build();

        cardEntitity = CardEntitity.builder()
                .estadoId(estado)
                .fechaVencimiento(cardDto.getFechaVencimiento())
                .fechaCreacion(cardDto.getFechaCreacion())
                .cardId(cardDto.getCardId())
                .nombreTitular(cardDto.getNombreTitular())
                .balance(cardDto.getBalance())
                .build();


    }

    @Test
    void generateCard() {

        Card cardSave = Card.builder()
                .estado(estados)
                .fechaVencimiento(LocalDate.now().plusYears(3))
                .fechaCreacion(LocalDate.now())
                .cardId(cardId)
                .nombreTitular("Jonathan romero")
                .balance(0.0)
                .build();

        when(repositotyMapper.mapCard(cardSave)).thenReturn(cardEntitity);
        when(cardEntityRepository.save(cardEntitity)).thenReturn(cardEntitity);
        when(repositotyMapper.mapEntityCard(any(CardEntitity.class))).thenReturn(cardDto);

        Card cardObtenido = cardRepositoryAdapter.generateCard(cardDto);

        assertEquals(cardEntitity.getCardId(), cardObtenido.getCardId());

    }

    @Test
    void findByCardId() {

        when(cardEntityRepository.findById(cardId)).thenReturn(Optional.of(cardEntitity));
        when(repositotyMapper.mapEntityCard(any(CardEntitity.class))).thenReturn(cardDto);

        Card card = cardRepositoryAdapter.findByCardId(cardId);

        assertEquals(cardId, card.getCardId());
    }

    @Test
    void activeCardOrBlockOrBalance() {
        Card cardUpdateEstado = Card.builder()
                .estado(estados)
                .fechaVencimiento(LocalDate.now().plusYears(3))
                .fechaCreacion(LocalDate.now())
                .cardId(cardId)
                .nombreTitular("Jonathan romero")
                .balance(0.0)
                .build();

        when(repositotyMapper.mapCard(cardUpdateEstado)).thenReturn(cardEntitity);
        when(cardEntityRepository.save(cardEntitity)).thenReturn(cardEntitity);
        when(repositotyMapper.mapEntityCard(any(CardEntitity.class))).thenReturn(cardDto);

        Card cardObtenido = cardRepositoryAdapter.generateCard(cardDto);

        assertEquals(cardEntitity.getCardId(), cardObtenido.getCardId());
    }
}