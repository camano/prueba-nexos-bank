package com.prueba.nexos.prueba.infrastructure.controller.controller;

import com.prueba.nexos.prueba.aplication.services.TransactionService;
import com.prueba.nexos.prueba.domain.domain.transaction.response.TransactionResponse;
import com.prueba.nexos.prueba.infrastructure.controller.models.request.TransactionPurchaseRequest;
import com.prueba.nexos.prueba.infrastructure.controller.models.request.TransactionRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transaction")

public class TransactionController {

    @Autowired
    private TransactionService transactionService;


    @Operation(description = "Se consultara la transaccion ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "consultar transacción ",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponse.class))
                    }
            )
    })
    @GetMapping("/{transactionId}")
    public ResponseEntity<?> consultarTransacionController(@PathVariable Long transactionId){
        return new ResponseEntity<>(transactionService.consultarTransacion(transactionId), HttpStatus.OK);
    }

    @Operation(description = "para anular la transaccion no se debe pasar  las 24 horas y se devolvera el saldo de la tarjeta ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se anula  la transacción ",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponse.class))
                    }
            )
    })
    @PostMapping("/anulation")
    public ResponseEntity<?> anularTranascionController(@RequestBody TransactionRequest transactionRequest){
        return new ResponseEntity<>(transactionService.anularTransacion(transactionRequest),HttpStatus.OK);
    }

    @Operation(description = "Se realiza la transacción ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se inicia la transaccion ",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = TransactionResponse.class))
                    }
            )
    })
    @PostMapping("/purchase")
    public ResponseEntity<?>compraTransacionController(@RequestBody TransactionPurchaseRequest transactionPurchaseRequest){
        return new ResponseEntity<>(transactionService.transacionCompra(transactionPurchaseRequest),HttpStatus.OK);
    }

}
