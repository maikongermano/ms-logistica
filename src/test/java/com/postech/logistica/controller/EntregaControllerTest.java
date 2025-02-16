package com.postech.logistica.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.postech.logistica.dto.AtualizaStatusEntregaDTO;
import com.postech.logistica.entity.Entrega;
import com.postech.logistica.service.EntregaService;

@ExtendWith(MockitoExtension.class)
class EntregaControllerTest {

    @Mock
    private EntregaService entregaService;

    @InjectMocks
    private EntregaController entregaController;

    @Test
    void deveAtualizarStatusEntrega() {
        AtualizaStatusEntregaDTO dto = new AtualizaStatusEntregaDTO();
        dto.setStatus("EM TRANSPORTE");

        Entrega entrega = new Entrega();
        entrega.setId(1L);
        entrega.setStatus("EM TRANSPORTE");

        when(entregaService.atualizarStatus(1L, "EM TRANSPORTE")).thenReturn(entrega);

        ResponseEntity<Entrega> response = entregaController.atualizarStatus(1L, dto);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("EM TRANSPORTE", response.getBody().getStatus());

        verify(entregaService, times(1)).atualizarStatus(1L, "EM TRANSPORTE");
    }
}
