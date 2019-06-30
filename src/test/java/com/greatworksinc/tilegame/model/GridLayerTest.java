package com.greatworksinc.tilegame.model;

import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.google.common.truth.Truth.assertThat;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class GridLayerTest extends Mockito {

  private @Mock GridDataSource gridDataSource;
  private GridLayer layer2x3;

  @BeforeEach
  void setUp() {
    doReturn(ImmutableMap.builder()
        .put(GridLocation.of(0, 0), 1)
        .put(GridLocation.of(0, 1), 2)
        .put(GridLocation.of(0, 2), 3)
        .put(GridLocation.of(1, 0), 4)
        .put(GridLocation.of(1, 1), 5)
        .put(GridLocation.of(1, 2), 6)
        .build()).when(gridDataSource).getDataAsMap(0);
    layer2x3 = new GridLayer(gridDataSource, GridSize.of(2, 3));
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