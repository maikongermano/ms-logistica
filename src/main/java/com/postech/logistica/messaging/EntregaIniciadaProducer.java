package com.postech.logistica.messaging;

import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.postech.logistica.dto.EntregaIniciadaEvento;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EntregaIniciadaProducer {

    private final StreamBridge streamBridge;

    public void enviarEventoEntregaIniciada(Long pedidoId) {
        EntregaIniciadaEvento evento = new EntregaIniciadaEvento(pedidoId, "ENTREGA_INICIADA");
        System.out.println("Enviando evento de entrega iniciada: " + evento);
        streamBridge.send("entregaIniciadaProducer-out-0", evento);
    }
}
