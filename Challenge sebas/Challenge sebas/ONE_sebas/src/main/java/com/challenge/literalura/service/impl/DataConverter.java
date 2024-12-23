package com.challenge.literalura.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConverter {

  private final ObjectMapper mapper = new ObjectMapper();

  // Conversión del JSON a la entidad correspondiente
  public <T> T getData(String json, Class<T> entidad) {
    try {
      return mapper.readValue(json, entidad);
    } catch (JsonProcessingException e) {
      // Mensaje de error detallado
      throw new RuntimeException("Error al convertir el JSON a la entidad: " + e.getMessage(), e);
    }
  }
}
