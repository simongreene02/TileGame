package com.greatworksinc.tilegame.model;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class GridSizeTest {

  @Test
  void init_positive_success() {
    new GridSize(1, 1);
  }

  @Test
  void init_zero_error() {
    assertThrows(IllegalArgumentException.class,
        () -> new GridSize(0, 0));
  }

  @Test
  void init_negative_error() {
    assertThrows(IllegalArgumentException.class,
        () -> new GridSize(-1, -1));
  }
}