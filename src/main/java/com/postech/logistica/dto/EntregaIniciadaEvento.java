package com.postech.logistica.dto;

import com.postech.logistica.enums.StatusEntrega;

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
public class EntregaIniciadaEvento {
    private Long pedidoId;
    private StatusEntrega status;
}
