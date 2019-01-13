package com.greatworksinc.tilegame.model;

import com.google.common.testing.EqualsTester;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

class GridLocationTest {

  @Test
  void getRow() {
    assertThat(GridLocation.of(7, 0).getRow()).isEqualTo(7);
  }

  @Test
  void getCol() {
    assertThat(GridLocation.of(0, 8).getCol()).isEqualTo(8);
  }

  @Test
  void equalities() {
    new EqualsTester()
        .addEqualityGroup(GridLocation.of(0, 0), GridLocation.of(0, 0))
        .addEqualityGroup(GridLocation.of(0, 1))
        .addEqualityGroup(GridLocation.of(1, 0))
        .testEquals();
  }
}