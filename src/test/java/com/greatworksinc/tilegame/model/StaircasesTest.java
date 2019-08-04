package com.greatworksinc.tilegame.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.truth.Truth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class StaircasesTest {

  private static final String DATA = "{\"upStair\":{\"row\":0,\"col\":7},\"downStair\":{\"row\":10,\"col\":5}}";
  private static final Staircases STAIRCASES = new Staircases(GridLocation.of(0, 7), GridLocation.of(10, 5));
  private ObjectMapper objectMapper;

  @BeforeEach
  void init() {
    objectMapper = new ObjectMapper();
  }

  @Test
  void deserialize() throws IOException {
    Staircases out = objectMapper.readValue(DATA, Staircases.class);
    Truth.assertThat(out).isEqualTo(STAIRCASES);
  }

  @Test
  void serialize() throws IOException {
    String out = objectMapper.writeValueAsString(STAIRCASES);
    Truth.assertThat(out).isEqualTo(DATA);
  }
}