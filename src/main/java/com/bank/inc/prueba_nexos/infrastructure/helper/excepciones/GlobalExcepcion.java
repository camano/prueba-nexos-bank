package com.bank.inc.prueba_nexos.infrastructure.helper.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExcepcion extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<MensajeException> manejarGlobalException(BusinessException businessException, WebRequest webRequest) {
        MensajeException  mensajeException = MensajeException.builder().mensaje(businessException.getMessage()).build();
        return new ResponseEntity<>(mensajeException, HttpStatus.BAD_REQUEST);
    }
}
