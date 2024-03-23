package com.prueba.nexos.prueba.infrastructure.helper.utils;

import org.springframework.stereotype.Component;

import java.util.Random;


@Component
public class Utilidades {


    public String generarNumeroAleatorio() {
        Random random = new Random();
        StringBuilder numero = new StringBuilder();

        // Garantiza que el primer dígito no sea cero
        numero.append((1 + random.nextInt(9)));

        // Genera el resto de los dígitos
        for (int i = 1; i < 10; i++) {
            numero.append(random.nextInt(10));
        }

        return Long.toString(Long.parseLong(numero.toString()));
    }

}
