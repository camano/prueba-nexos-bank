package com.prueba.nexos.prueba.domain.domain.status.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Estado {

    private Long estadoId;

    private String nombreEstado;
}
