package com.prueba.nexos.prueba.infrastructure.controller.controller;


import com.prueba.nexos.prueba.aplication.services.CardService;
import com.prueba.nexos.prueba.domain.model.response.CardResponse;
import com.prueba.nexos.prueba.infrastructure.controller.models.request.CardRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/card")
public class CardController {


    @Autowired
    private CardService cardService;

    @Operation(description = "Generar la tarjeta con su respectivo cardId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Generar la tarjeta con su respectivo cardId ",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CardResponse.class))
                    }
            )
    })
    @GetMapping("/{productoId}/number")
    public ResponseEntity<CardResponse> generateCardPort(@PathVariable String productoId) {
        return new ResponseEntity<>(cardService.generarCard(productoId), HttpStatus.OK);
    }

    @Operation(description = "Activar tarjeta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Activar la tarjeta ",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CardResponse.class))
                    }
            )
    })
    @PostMapping("/enroll")
    public ResponseEntity<Optional<CardResponse>> activateCardPort(@RequestBody CardRequest card) {
        return new ResponseEntity<>(cardService.activeCard(card.getCardId()), HttpStatus.OK);
    }

    @Operation(description = "Se bloqueara la tarjeta ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "La tarjeta se cambia el estado a bloqueada ",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CardResponse.class))
                    }
            )
    })
    @DeleteMapping("/{cardId}")
    public ResponseEntity<?> blockCardPort(@PathVariable String cardId) {
        return new ResponseEntity<>(cardService.blockCard(cardId), HttpStatus.OK);
    }

    @Operation(description = "Recargar saldo ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se recarga el saldo de la tarjeta",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CardResponse.class))
                    }
            )
    })
    @PostMapping("/balance")
    public ResponseEntity<?> rechangeBalanceCardPort(@RequestBody CardRequest cardRequest) {
        return new ResponseEntity<>(cardService.rechangeBalance(cardRequest), HttpStatus.OK);
    }

    @Operation(description = "Se consulta el saldo de la tarjeta ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se consulta el saldo",
                    content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = CardResponse.class))
                    }
            )
    })
    @GetMapping("/balance/{cardId}")
    public ResponseEntity<?> checkBalance(@PathVariable String cardId) {
        return new ResponseEntity<>(cardService.checkBalance(cardId), HttpStatus.OK);
    }
}
