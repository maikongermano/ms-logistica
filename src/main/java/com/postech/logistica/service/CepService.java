package com.postech.logistica.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class CepService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${spring.google.maps.api-key}")
    private String apiKey;

    private static final String GOOGLE_MAPS_API_URL = "https://maps.googleapis.com/maps/api/geocode/json?address=%s&key=%s";

    public double[] buscarLatitudeLongitude(String cep) {
        try {
            URI uri = new URI(String.format(GOOGLE_MAPS_API_URL, cep, apiKey));
            String response = restTemplate.getForObject(uri, String.class);

            JsonNode root = objectMapper.readTree(response);
            JsonNode locationNode = root.path("results").path(0).path("geometry").path("location");

            double latitude = locationNode.path("lat").asDouble();
            double longitude = locationNode.path("lng").asDouble();

            return new double[]{latitude, longitude};
        } catch (Exception e) {
            System.err.println("Erro ao buscar coordenadas para o CEP " + cep + ": " + e.getMessage());
            return new double[]{0.0, 0.0};
        }
    }
}
