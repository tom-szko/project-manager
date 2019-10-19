package com.szkopinski.projectmanager.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class TestHelpers {

  private static final ObjectMapper mapper = getObjectMapper();

  public static <T> String convertToJson(T t) throws JsonProcessingException {
    return mapper.writeValueAsString(t);
  }

  public static <T> String convertToJson(Iterable<T> t) throws JsonProcessingException {
    return mapper.writeValueAsString(t);
  }

  private static ObjectMapper getObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    return mapper;
  }
}