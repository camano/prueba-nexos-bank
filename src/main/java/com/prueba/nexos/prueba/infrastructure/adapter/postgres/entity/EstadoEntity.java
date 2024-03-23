package com.prueba.nexos.prueba.infrastructure.adapter.postgres.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "estado")
public class EstadoEntity {

    @Id
    @Column(name = "estadoId")
    private Long estadoId;

    @Column(name = "nombreEstado")
    private String nombreEstado;

}
