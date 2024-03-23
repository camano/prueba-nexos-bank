package com.prueba.nexos.prueba.domain.domain.transaction.response;

import com.prueba.nexos.prueba.domain.domain.transaction.request.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransactionResponse {

    private String mensaje ;

    private Transaction transaction;
}
