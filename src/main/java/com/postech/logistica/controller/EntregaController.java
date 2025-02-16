package com.postech.logistica.controller;



import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.postech.logistica.dto.AtualizaStatusEntregaDTO;
import com.postech.logistica.entity.Entrega;
import com.postech.logistica.service.EntregaService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/entregas")
@RequiredArgsConstructor
public class EntregaController {

    private final EntregaService entregaService;

    @GetMapping
    public List<Entrega> listarEntregas() {
        return entregaService.listarEntregas();
    }

    /**
     * Busca entregas próximas a uma localização dentro de um raio específico.
     */
    @GetMapping("/proximas")
    public List<Entrega> buscarEntregasProximas(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "10") double raioKm) {
        return entregaService.buscarEntregasProximas(latitude, longitude, raioKm);
    }
    
    @PutMapping("/{id}/status")
    public ResponseEntity<Entrega> atualizarStatus(@PathVariable Long id, @RequestBody AtualizaStatusEntregaDTO dto) {
        Entrega entregaAtualizada = entregaService.atualizarStatus(id, dto.getStatus());
        return ResponseEntity.ok(entregaAtualizada);
    }
}

