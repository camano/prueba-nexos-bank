package com.prueba.nexos.prueba.infrastructure.adapter.postgres.mapper;

import com.prueba.nexos.prueba.domain.domain.status.request.Estado;
import com.prueba.nexos.prueba.domain.domain.transaction.request.Transaction;
import com.prueba.nexos.prueba.domain.model.request.Card;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.entity.CardEntitity;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.entity.EstadoEntity;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.entity.TransactionEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RepositotyMapper {

    @Autowired
    private ModelMapper modelMapper;

    public RepositotyMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    public Transaction mapEntityTransaction(TransactionEntity transactionEntity){
        transactionEntity.setEstadoId(transactionEntity.getEstadoId());

        return this.modelMapper.map(transactionEntity,Transaction.class);

    }

    public TransactionEntity mapTransaction(Transaction transaction){
        return this.modelMapper.map(transaction,TransactionEntity.class);
    }

    public Estado mapEntityEstados(EstadoEntity estadoEntity){
        return this.modelMapper.map(estadoEntity,Estado.class);
    }

    public EstadoEntity mapEstados(Estado estados){
        return this.modelMapper.map(estados,EstadoEntity.class);
    }

    public CardEntitity mapCard(Card card){
        return this.modelMapper.map(card,CardEntitity.class);
    }

    public Card mapEntityCard(CardEntitity cardEntitity){

        return Card.builder()
                .cardId(cardEntitity.getCardId())
                .balance(cardEntitity.getBalance())
                .fechaCreacion(cardEntitity.getFechaCreacion())
                .nombreTitular(cardEntitity.getNombreTitular())
                .fechaVencimiento(cardEntitity.getFechaVencimiento())
                .estado(Estado.builder()
                        .nombreEstado(cardEntitity.getEstadoId().getNombreEstado())
                        .estadoId(cardEntitity.getEstadoId().getEstadoId())
                        .build())
                .build();
    }


}
