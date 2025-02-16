package com.postech.logistica.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EntregaIniciadaEvento {
    private Long pedidoId;
    private String status;
}
