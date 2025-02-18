package com.postech.logistica.service;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.postech.logistica.dto.NovoPedidoDTO;
import com.postech.logistica.entity.Entrega;
import com.postech.logistica.enums.StatusEntrega;
import com.postech.logistica.messaging.StatusEntregaProducer;
import com.postech.logistica.repository.EntregaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EntregaService {

    private final EntregaRepository entregaRepository;
    private final StatusEntregaProducer statusEntregaProducer;
    private final CepService cepService;
    private static final double RAIO_TERRA_KM = 6371.0;

    public Entrega criarEntrega(NovoPedidoDTO novoPeditoDTO) {
    	double[] coordenadas = cepService.buscarLatitudeLongitude(novoPeditoDTO.getCep());
        double latitude = coordenadas[0];
        double longitude = coordenadas[1];
        
        Entrega entrega = Entrega.builder()
                .pedidoId(novoPeditoDTO.getIdPedido())
                .endereco(novoPeditoDTO.getCep())
                .latitude(latitude)
                .longitude(longitude)
                .status(StatusEntrega.EM_SEPARACAO)
                .dataCriacao(LocalDateTime.now())
                .build();
        return entregaRepository.save(entrega);
    }

    public List<Entrega> listarEntregas() {
        return entregaRepository.findAll();
    }

    /**
     * Busca entregas dentro de um raio de distância da localização informada.
     */
    public List<Entrega> buscarEntregasProximas(double latitude, double longitude, double raioKm) {
        return entregaRepository.findAll().stream()
                .filter(entrega -> calcularDistancia(latitude, longitude, entrega.getLatitude(), entrega.getLongitude()) <= raioKm)
                .collect(Collectors.toList());
    }

    /**
     * Calcula a distância entre dois pontos geográficos (Fórmula de Haversine).
     */
    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return RAIO_TERRA_KM * c;
    }
    
    public Entrega atualizarStatus(Long entregaId, StatusEntrega novoStatus) {
        Optional<Entrega> entregaOpt = entregaRepository.findById(entregaId);
        if (entregaOpt.isEmpty()) {
            throw new RuntimeException("Entrega não encontrada!");
        }

        Entrega entrega = entregaOpt.get();
        entrega.setStatus(novoStatus);
        entregaRepository.save(entrega);

        statusEntregaProducer.enviarEventoStatusEntrega(entrega.getId(), novoStatus);

        return entrega;
    }
    
    public void cancelarEntrega(Long pedidoId) {
        Optional<Entrega> entregaOpt = entregaRepository.findById(pedidoId);
        if (entregaOpt.isPresent()) {
            Entrega entrega = entregaOpt.get();
            entrega.setStatus(StatusEntrega.CANCELADO);
            entregaRepository.save(entrega);
            System.out.println("Entrega cancelada para o pedido: " + pedidoId);
        } else {
            System.out.println("Nenhuma entrega encontrada para o pedido: " + pedidoId);
        }
    }
}

