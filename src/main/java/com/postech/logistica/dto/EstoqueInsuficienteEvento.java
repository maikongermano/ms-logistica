package com.postech.logistica.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EstoqueInsuficienteEvento {
    private Long pedidoId;
    private boolean estoqueSuficiente;
}
