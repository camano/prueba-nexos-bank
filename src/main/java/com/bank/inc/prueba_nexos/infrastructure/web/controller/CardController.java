package com.bank.inc.prueba_nexos.infrastructure.web.controller;

import com.bank.inc.prueba_nexos.application.usecase.CardUseCase;
import com.bank.inc.prueba_nexos.infrastructure.web.request.CardRequest;
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

    @GetMapping("/{productoId}/number")
    public ResponseEntity<?> generateCardPort(@PathVariable String productoId) {
        return new ResponseEntity<>(cardUseCase.issueCard(productoId), HttpStatus.OK);
    }

    @PostMapping("/enroll")
    public ResponseEntity<?> activateCardPort(@RequestBody CardRequest card) {
        return new ResponseEntity<>(cardUseCase.activateCard(card), HttpStatus.OK);
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<?> blockCardPort(@PathVariable String cardId) {
        return new ResponseEntity<>(cardUseCase.blockCard(cardId), HttpStatus.OK);
    }

    @PostMapping("/balance")
    public ResponseEntity<?> rechangeBalanceCardPort(@RequestBody CardRequest cardRequest) {
        return new ResponseEntity<>(cardUseCase.recharge(cardRequest.cardId(),cardRequest.balance()), HttpStatus.OK);
    }

    @GetMapping("/balance/{cardId}")
    public ResponseEntity<?> checkBalance(@PathVariable String cardId) {
        return new ResponseEntity<>(cardUseCase.checkBalance(cardId), HttpStatus.OK);
    }
}
