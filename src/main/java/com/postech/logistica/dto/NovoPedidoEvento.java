package com.postech.logistica.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class NovoPedidoEvento {
    private Long pedidoId;
    private String endereco;
    private Double latitude;
    private Double longitude;
}
