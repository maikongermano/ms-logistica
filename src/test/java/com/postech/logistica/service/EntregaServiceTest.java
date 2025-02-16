package com.postech.logistica.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.postech.logistica.entity.Entrega;
import com.postech.logistica.messaging.StatusEntregaProducer;
import com.postech.logistica.repository.EntregaRepository;

@ExtendWith(MockitoExtension.class)
class EntregaServiceTest {

    @Mock
    private EntregaRepository entregaRepository;

    @Mock
    private StatusEntregaProducer statusEntregaProducer;

    @InjectMocks
    private EntregaService entregaService;

    private Entrega entrega;

    @BeforeEach
    void setUp() {
        entrega = new Entrega();
        entrega.setId(1L);
        entrega.setPedidoId(1001L);
        entrega.setStatus("PENDENTE");
    }

    @Test
    void deveAtualizarStatusEntrega() {
        when(entregaRepository.findById(1L)).thenReturn(Optional.of(entrega));

        Entrega entregaAtualizada = entregaService.atualizarStatus(1L, "EM TRANSPORTE");

        assertEquals("EM TRANSPORTE", entregaAtualizada.getStatus());

        verify(entregaRepository, times(1)).save(entrega);

        verify(statusEntregaProducer, times(1)).enviarEventoStatusEntrega(1L, "EM TRANSPORTE");
    }
}
