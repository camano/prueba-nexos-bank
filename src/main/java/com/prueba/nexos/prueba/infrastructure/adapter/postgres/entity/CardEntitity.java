package com.prueba.nexos.prueba.infrastructure.adapter.postgres.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "card")
public class CardEntitity {

    @Id
    @Column(name = "cardId",length = 16)
    private String cardId;

    @Column(name = "nombreTitular")
    private String nombreTitular;

    @Column(name = "fechaCreacion")
    private LocalDate fechaCreacion;

    @Column(name = "fechaVencimiento")
    private LocalDate fechaVencimiento;


    @Column(name = "saldo")
    private Double balance;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estadoIdCard",nullable = false)
    private EstadoEntity estadoId;


}
