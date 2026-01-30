package com.bank.inc.prueba_nexos.infrastructure.helper.excepciones;

public class BusinessException extends RuntimeException{

    public BusinessException(String mensaje) {
        super(mensaje);
    }
}
