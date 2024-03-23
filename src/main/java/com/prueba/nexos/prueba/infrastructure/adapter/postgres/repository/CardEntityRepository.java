package com.prueba.nexos.prueba.infrastructure.adapter.postgres.repository;

import com.prueba.nexos.prueba.infrastructure.adapter.postgres.entity.CardEntitity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CardEntityRepository extends JpaRepository<CardEntitity,String>  {
}
