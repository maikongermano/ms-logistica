package com.postech.logistica.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.postech.logistica.service.CepService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/geolocalizacao")
@RequiredArgsConstructor
public class GeolocalizacaoController {

    private final CepService cepService;

    @GetMapping("/{cep}")
    public ResponseEntity<double[]> obterCoordenadas(@PathVariable String cep) {
        double[] coordenadas = cepService.buscarLatitudeLongitude(cep);
        return ResponseEntity.ok(coordenadas);
    }
}
