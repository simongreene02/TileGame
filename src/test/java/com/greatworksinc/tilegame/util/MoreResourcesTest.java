package com.greatworksinc.tilegame.util;

import com.google.common.truth.Truth;
import org.junit.jupiter.api.Test;

class MoreResourcesTest {
  @Test
  void getResource() {
    Truth.assertThat(MoreResources.getResource("Castle2.png").toString()).
        isEqualTo("file:/home/ninja-jr/IdeaProjects/TileGame/out/production/resources/Castle2.png");
  }
}