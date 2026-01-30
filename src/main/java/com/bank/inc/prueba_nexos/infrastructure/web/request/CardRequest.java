package com.bank.inc.prueba_nexos.infrastructure.web.request;

import java.math.BigDecimal;

public record CardRequest(String cardId, BigDecimal balance) {
}
