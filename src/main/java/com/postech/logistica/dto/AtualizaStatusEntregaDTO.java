package com.postech.logistica.dto;

import com.postech.logistica.enums.StatusEntrega;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AtualizaStatusEntregaDTO {
    private StatusEntrega status;
}
