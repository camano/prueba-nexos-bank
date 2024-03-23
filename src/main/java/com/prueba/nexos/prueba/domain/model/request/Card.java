package com.prueba.nexos.prueba.domain.model.request;


import com.prueba.nexos.prueba.domain.domain.status.request.Estado;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Card {

    private String cardId;

    private String nombreTitular;

    private LocalDate fechaCreacion;

    private LocalDate fechaVencimiento;


    private Double balance;

    private Estado estado;

}
