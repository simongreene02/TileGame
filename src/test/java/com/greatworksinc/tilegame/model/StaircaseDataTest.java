package com.greatworksinc.tilegame.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.truth.Truth;
import org.junit.jupiter.api.Test;

import java.io.IOException;

class StaircaseDataTest {
  @Test
  void deserialize() throws IOException {
    String data = "{\"row\":10,\"col\":5,\"gid\":57}";
    ObjectMapper objectMapper = new ObjectMapper();
    StaircaseData out = objectMapper.readValue(data, StaircaseData.class);
    Truth.assertThat(out).isEqualTo(new StaircaseData(10, 5, 57));
  }

  @Test
  void serialize() throws IOException {
    String data = "{\"row\":10,\"col\":5,\"gid\":57}";
    ObjectMapper objectMapper = new ObjectMapper();
    String out = objectMapper.writeValueAsString(new StaircaseData(10, 5, 57));
    Truth.assertThat(out).isEqualTo(data);
  }
}