package com.prueba.nexos.prueba.infrastructure.adapter.postgres.repository;

import com.prueba.nexos.prueba.infrastructure.adapter.postgres.entity.CardEntitity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CardEntityRepositoryTest {

    @Autowired
    private CardEntityRepository cardEntityRepository;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testConsultarCardId() {
        String cardId = "1234567674555246";
        Optional<CardEntitity> card = cardEntityRepository.findById(cardId);
        assertEquals(card.get().getCardId(), cardId);
    }
}