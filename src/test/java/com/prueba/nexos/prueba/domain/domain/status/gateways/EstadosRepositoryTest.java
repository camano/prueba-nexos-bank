package com.prueba.nexos.prueba.domain.domain.status.gateways;

import com.prueba.nexos.prueba.domain.domain.status.request.Estado;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.RepositoryAdapter.EstadosRepositoryAdapter;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.entity.EstadoEntity;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.mapper.RepositotyMapper;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.repository.EstadoEntityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class EstadosRepositoryTest {


    @Mock
    private EstadoEntityRepository estadoEntityRepository;

    @Mock
    private RepositotyMapper repositotyMapper;

    @InjectMocks
    private EstadosRepositoryAdapter estadosRepositoryAdapter;

    @BeforeEach
    void setUp() {
    }

    @Test
    void estados() {
        Long estadoId = 1L;
        EstadoEntity estado = EstadoEntity.builder().nombreEstado("Aprobada").estadoId(1L).build();
        Estado estadoObtenido = Estado.builder().nombreEstado("Aprobada").estadoId(1L).build();
        when(estadoEntityRepository.findById(estadoId)).thenReturn(Optional.of(estado));
        when(repositotyMapper.mapEntityEstados(estado)).thenReturn(estadoObtenido);

        Estado estados=estadosRepositoryAdapter.estados(estadoId);

        assertEquals(1L,estados.getEstadoId());
    }
}