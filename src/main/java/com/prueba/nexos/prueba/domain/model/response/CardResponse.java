package com.prueba.nexos.prueba.domain.model.response;


import com.prueba.nexos.prueba.domain.model.request.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardResponse {

    private String mensaje;

    private Card card;

}
