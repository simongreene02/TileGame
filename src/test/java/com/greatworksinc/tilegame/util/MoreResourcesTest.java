package com.greatworksinc.tilegame.util;

import com.google.common.truth.Truth;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

class MoreResourcesTest {
  @Test
  void getResource() throws URISyntaxException, IOException {
    Truth.assertThat(Files.size(Paths.get(MoreResources.getResource("test.png").toURI()))).isEqualTo(149890);
  }
}