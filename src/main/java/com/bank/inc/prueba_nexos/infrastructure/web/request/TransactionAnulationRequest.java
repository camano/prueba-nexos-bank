package com.bank.inc.prueba_nexos.infrastructure.web.request;

public record TransactionAnulationRequest(String cardId,Long transactionId) {
}
