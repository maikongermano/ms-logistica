package com.postech.logistica.messaging;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.postech.logistica.dto.EntregaIniciadaEvento;
import com.postech.logistica.enums.StatusEntrega;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EntregaIniciadaProducer {

    private final StreamBridge streamBridge;

    public void enviarEventoEntregaIniciada(Long pedidoId) {
        EntregaIniciadaEvento evento = new EntregaIniciadaEvento(pedidoId, StatusEntrega.INICIADO);
        System.out.println("Enviando evento de entrega iniciada: " + evento);
        streamBridge.send("entregaIniciadaProducer-out-0", evento);
    }
}
