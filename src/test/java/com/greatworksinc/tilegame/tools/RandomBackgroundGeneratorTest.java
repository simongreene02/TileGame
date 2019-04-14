package com.greatworksinc.tilegame.tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RandomBackgroundGeneratorTest {

  @BeforeEach
  void setUp() {
  }

  /**
   * p = (5,7)
   *
   * f(p, maze, 0) = (7,7)
   * f(p, maze, 90) = (5,5)
   * f(p, maze, 180) = (3,7)
   * f(p, maze, 270) = (5,9)
   */
  @Test
  void isTileInDirection() {
    boolean[][] maze = {{true,false,true,true,true,true,true,true,true,true,true},
        {true,false,true,false,false,false,false,false,false,false,true},
        {true,true,true,false,true,false,true,true,true,true,true},
        {false,false,true,false,true,false,true,false,false,false,false},
        {true,false,true,false,true,false,true,true,true,false,true},
        {true,false,true,false,true,false,false,false,true,false,true},
        {true,true,true,false,true,false,true,false,true,false,true},
        {false,false,false,false,true,false,true,false,true,false,true},
        {true,true,true,true,true,false,true,true,true,true,true},
        {true,false,false,false,false,false,false,false,true,false,false},
        {true,true,true,true,true,true,true,true,true,false,false}};

    assertThat(RandomBackgroundGenerator.isTileInDirection(new Point(8, 10), 0, maze)).isFalse();
    assertThat(RandomBackgroundGenerator.isTileInDirection(new Point(10, 8), 270, maze)).isFalse();
  }
}