package com.bank.inc.prueba_nexos.infrastructure.web.controller;

import com.bank.inc.prueba_nexos.application.usecase.TransactionUseCase;
import com.bank.inc.prueba_nexos.infrastructure.web.request.TransactionAnulationRequest;
import com.bank.inc.prueba_nexos.infrastructure.web.request.TransactionRequest;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionUseCase transactionUseCase;

    public TransactionController(TransactionUseCase transactionUseCase) {
        this.transactionUseCase = transactionUseCase;
    }

    @Operation(description = "Se ralizara la transacción ")
    @PostMapping("/purchase")
    public ResponseEntity<?> compraTransacionController(@RequestBody TransactionRequest transactionPurchaseRequest){
        return new ResponseEntity<>(transactionUseCase.purchase(transactionPurchaseRequest), HttpStatus.CREATED);
    }

    @Operation(description = "Se consultara la transaccion ")
    @GetMapping("/{transactionId}")
    public ResponseEntity<?> consultarTransacionController(@PathVariable Long transactionId){
        return new ResponseEntity<>(transactionUseCase.findByTransation(transactionId), HttpStatus.OK);
    }

    @Operation(description = "para anular la transaccion no se debe pasar  las 24 horas y se devolvera el saldo de la tarjeta ")
    @PostMapping("/anulation")
    public ResponseEntity<?> anularTranascionController(@RequestBody TransactionAnulationRequest transactionRequest){
        return new ResponseEntity<>(transactionUseCase.reverseTransaction(transactionRequest),HttpStatus.OK);
    }
}
