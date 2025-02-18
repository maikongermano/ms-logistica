package com.postech.logistica.dto;

import java.util.Map;

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
public class NovoPedidoDTO {
    private Long idPedido;
    private String cep;
    private Map<Long, Integer> itens;
}
