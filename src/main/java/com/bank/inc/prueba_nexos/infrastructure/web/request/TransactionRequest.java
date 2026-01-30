package com.bank.inc.prueba_nexos.infrastructure.web.request;

import java.math.BigDecimal;

public record TransactionRequest(String cardId, BigDecimal price) {
}
