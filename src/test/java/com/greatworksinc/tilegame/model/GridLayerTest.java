package com.greatworksinc.tilegame.model;

import com.greatworksinc.tilegame.util.MoreResources;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

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

  @ParameterizedTest
  @CsvFileSource(resources = "/testLevel2x3Params.csv")
  void getGidByLocation(String row, String col, String gid) {
    assertThat(layer2x3.getGidByLocation(GridLocation.of(Integer.parseInt(row), Integer.parseInt(col)))).isEqualTo(Integer.parseInt(gid));
  }
}