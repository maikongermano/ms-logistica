package com.postech.logistica.messaging;

import com.postech.logistica.dto.StatusEntregaEvento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Component
public class StatusEntregaProducer {

    private final StreamBridge streamBridge;

    @Autowired
    public StatusEntregaProducer(StreamBridge streamBridge) {
        this.streamBridge = streamBridge;
    }

    public void enviarEventoStatusEntrega(Long entregaId, String status) {
        StatusEntregaEvento evento = new StatusEntregaEvento(entregaId, status);
        Message<StatusEntregaEvento> message = MessageBuilder.withPayload(evento).build();

        streamBridge.send("statusEntregaProducer-out-0", message);

        System.out.println("📢 Evento de status enviado para Kafka: " + evento);
    }
}
