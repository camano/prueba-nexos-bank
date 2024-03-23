package com.prueba.nexos.prueba.infrastructure.adapter.postgres.RepositoryAdapter;


import com.prueba.nexos.prueba.domain.domain.status.gateways.EstadosRepository;
import com.prueba.nexos.prueba.domain.domain.status.request.Estado;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.mapper.RepositotyMapper;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.repository.EstadoEntityRepository;
import com.prueba.nexos.prueba.infrastructure.helper.excepciones.Excepctiones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstadosRepositoryAdapter implements EstadosRepository {

    @Autowired
    private EstadoEntityRepository estadoEntityRepository;

    @Autowired
    private RepositotyMapper repositotyMapper;

    public EstadosRepositoryAdapter(EstadoEntityRepository estadoEntityRepository, RepositotyMapper repositotyMapper) {
        this.estadoEntityRepository = estadoEntityRepository;
        this.repositotyMapper = repositotyMapper;
    }

    @Override
    public Estado estados(Long estadoId) {
        return estadoEntityRepository.findById(estadoId)
                .map(estadoEntity -> repositotyMapper.mapEntityEstados(estadoEntity))
                .orElseThrow(() -> new Excepctiones("No se encontro el estado "));
    }


}
