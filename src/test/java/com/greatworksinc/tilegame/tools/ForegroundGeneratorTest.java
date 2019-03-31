package com.greatworksinc.tilegame.tools;

import com.google.common.collect.ImmutableMap;
import com.greatworksinc.tilegame.model.GridLocation;
import com.greatworksinc.tilegame.model.GridSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ForegroundGeneratorTest {

  @Test
  void positive_5x5() {
    ImmutableMap<GridLocation, Integer> expectedResult = ImmutableMap.<GridLocation, Integer>builder()
        .put(GridLocation.of(0, 0), 58)
        .put(GridLocation.of(0, 1), 0)
        .put(GridLocation.of(0, 2), 0)
        .put(GridLocation.of(0, 3), 0)
        .put(GridLocation.of(0, 4), 0)
        .put(GridLocation.of(1, 0), 0)
        .put(GridLocation.of(1, 1), 0)
        .put(GridLocation.of(1, 2), 0)
        .put(GridLocation.of(1, 3), 0)
        .put(GridLocation.of(1, 4), 0)
        .put(GridLocation.of(2, 0), 0)
        .put(GridLocation.of(2, 1), 0)
        .put(GridLocation.of(2, 2), 0)
        .put(GridLocation.of(2, 3), 0)
        .put(GridLocation.of(2, 4), 0)
        .put(GridLocation.of(3, 0), 0)
        .put(GridLocation.of(3, 1), 0)
        .put(GridLocation.of(3, 2), 0)
        .put(GridLocation.of(3, 3), 0)
        .put(GridLocation.of(3, 4), 0)
        .put(GridLocation.of(4, 0), 0)
        .put(GridLocation.of(4, 1), 0)
        .put(GridLocation.of(4, 2), 0)
        .put(GridLocation.of(4, 3), 0)
        .put(GridLocation.of(4, 4), 57)
        .build();
    assertThat(new ForegroundGenerator(GridSize.of(5, 5)).getDataAsMap()).containsExactlyEntriesIn(expectedResult);
  }
}