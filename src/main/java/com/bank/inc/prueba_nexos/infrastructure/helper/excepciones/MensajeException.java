package com.bank.inc.prueba_nexos.infrastructure.helper.excepciones;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MensajeException {

    private String mensaje;
}
