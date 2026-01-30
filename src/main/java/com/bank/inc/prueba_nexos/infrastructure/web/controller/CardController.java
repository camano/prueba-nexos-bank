package com.bank.inc.prueba_nexos.infrastructure.web.controller;

import com.bank.inc.prueba_nexos.application.usecase.CardUseCase;
import com.bank.inc.prueba_nexos.infrastructure.web.request.CardRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/card")
public class CardController {

    private final CardUseCase cardUseCase;

    public CardController(CardUseCase cardUseCase) {
        this.cardUseCase = cardUseCase;
    }

    @Operation(description = "Generar la tarjeta con su respectivo cardId")
    @GetMapping("/{productoId}/number")
    public ResponseEntity<?> generateCardPort(@PathVariable String productoId) {
        return new ResponseEntity<>(cardUseCase.issueCard(productoId), HttpStatus.CREATED);
    }

    @Operation(description = "Activar tarjeta")
    @PostMapping("/enroll")
    public ResponseEntity<?> activateCardPort(@RequestBody CardRequest card) {
        return new ResponseEntity<>(cardUseCase.activateCard(card), HttpStatus.CREATED);
    }

    @Operation(description = "Se bloqueara la tarjeta ")
    @DeleteMapping("/{cardId}")
    public ResponseEntity<?> blockCardPort(@PathVariable String cardId) {
        return new ResponseEntity<>(cardUseCase.blockCard(cardId), HttpStatus.OK);
    }

    @Operation(description = "Recargar saldo ")
    @PostMapping("/balance")
    public ResponseEntity<?> rechangeBalanceCardPort(@RequestBody CardRequest cardRequest) {
        return new ResponseEntity<>(cardUseCase.recharge(cardRequest.cardId(),cardRequest.balance()), HttpStatus.CREATED);
    }

    @Operation(description = "Se consulta el saldo de la tarjeta ")
    @GetMapping("/balance/{cardId}")
    public ResponseEntity<?> checkBalance(@PathVariable String cardId) {
        return new ResponseEntity<>(cardUseCase.checkBalance(cardId), HttpStatus.OK);
    }
}
