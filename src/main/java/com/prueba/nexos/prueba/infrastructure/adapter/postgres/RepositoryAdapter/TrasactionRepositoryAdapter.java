package com.prueba.nexos.prueba.infrastructure.adapter.postgres.RepositoryAdapter;

import com.prueba.nexos.prueba.domain.domain.transaction.gateways.TransationRepository;
import com.prueba.nexos.prueba.domain.domain.transaction.request.Transaction;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.entity.TransactionEntity;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.mapper.RepositotyMapper;
import com.prueba.nexos.prueba.infrastructure.adapter.postgres.repository.TransactionEntityRepository;
import com.prueba.nexos.prueba.infrastructure.helper.excepciones.Excepctiones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class TrasactionRepositoryAdapter implements TransationRepository {

    @Autowired
    private TransactionEntityRepository transationRepository;

    @Autowired
    private RepositotyMapper repositotyMapper;


    @Override
    public Transaction transactionCompra(Transaction transaction) {
        try {
            TransactionEntity transactionSave=transationRepository.save(repositotyMapper.mapTransaction(transaction));
            return repositotyMapper.mapEntityTransaction(transactionSave);
        }catch (DataAccessException e){
            throw new Excepctiones("Hubo un error en la transaction "+ transaction.getEstado().getNombreEstado());
        }
    }

    @Override
    public Transaction consultTransaction(Long transactioId) {
        return transationRepository.findById(transactioId)
                .map(transactionEntity -> repositotyMapper.mapEntityTransaction(transactionEntity))
                .orElseThrow(() -> new Excepctiones("No se encontro la transacion"));
    }

    @Override
    public Optional<Transaction> anularTransaction(Transaction transaction) {
        try {
            TransactionEntity transactionUpdate= transationRepository.save(repositotyMapper.mapTransaction(transaction));
            return Optional.of(repositotyMapper.mapEntityTransaction(transactionUpdate));
        }catch (DataAccessException e){
            throw new Excepctiones("No se pudo anular la transacion ");
        }

    }
}
