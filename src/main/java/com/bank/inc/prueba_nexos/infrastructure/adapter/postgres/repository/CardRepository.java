package com.bank.inc.prueba_nexos.infrastructure.adapter.postgres.repository;

import com.bank.inc.prueba_nexos.infrastructure.adapter.postgres.entity.CardEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CardRepository extends JpaRepository<CardEntity, String> {
}
