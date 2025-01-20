package org.literalura.literalura1.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DatosC implements IDatosC{
    private ObjectMapper objectMapper= new ObjectMapper();
    @Override
    public <T> T ObtenerDatos(String json, Class<T> T) {
        try {
            return objectMapper.readValue(json, T);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
