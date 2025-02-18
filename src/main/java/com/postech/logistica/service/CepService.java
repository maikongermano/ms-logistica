package com.postech.logistica.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CepService {

    private static final String API_URL = "https://viacep.com.br/ws/%s/json/";

    public double[] buscarLatitudeLongitude(String cep) {
        try {
            String url = String.format(API_URL, cep);
            RestTemplate restTemplate = new RestTemplate();
            String response = restTemplate.getForObject(url, String.class);
            JSONObject json = new JSONObject(response);
            
            double latitude = json.optDouble("latitude", 0.0);
            double longitude = json.optDouble("longitude", 0.0);
            
            return new double[]{latitude, longitude};
        } catch (Exception e) {
            System.err.println("Erro ao buscar coordenadas para o CEP " + cep + ": " + e.getMessage());
            return new double[]{0.0, 0.0};
        }
    }
}
