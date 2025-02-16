package com.postech.logistica.messaging;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.postech.logistica.dto.EntregaConcluidaEvento;
import com.postech.logistica.enums.StatusEntrega;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EntregaConcluidaProducer {

    private final StreamBridge streamBridge;

    public void enviarEventoEntregaConcluida(Long pedidoId) {
        EntregaConcluidaEvento evento = new EntregaConcluidaEvento(pedidoId, StatusEntrega.ENTREGA_CONCLUIDA);
        System.out.println("Enviando evento de entrega concluída: " + evento);
        streamBridge.send("entregaConcluidaProducer-out-0", evento);
    }
}

