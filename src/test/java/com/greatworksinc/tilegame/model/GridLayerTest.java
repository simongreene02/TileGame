package com.greatworksinc.tilegame.model;

import com.greatworksinc.tilegame.util.MoreResources;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

class GridLayerTest {

  private GridLayer layer2x3;

  @BeforeEach
  void setUp() {
    layer2x3 = new GridLayer(MoreResources.getResource("testLevel2x3.csv"));
  }

  @Test
  void getGridSize() {
    assertThat(layer2x3.getGridSize()).isEqualTo(GridSize.of(2, 3));
  }

  @Test
  void getGidByLocation() {
    //assertThat(layer2x3.getGidByLocation());
  }
}