package com.greatworksinc.tilegame.tools;

import com.greatworksinc.tilegame.model.GridLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.awt.*;
import java.util.stream.Stream;

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
   * f(p, maze, 90) = (5,9)
   * f(p, maze, 180) = (3,7)
   * f(p, maze, 270) = (5,5)
   */
  @Test
  void isTileInDirection_specific() {
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

  @ParameterizedTest
  @MethodSource("isTileInDirectionGeneralStream")
  void isTileInDirection_general(Point location, int direction, boolean result) {
    boolean[][] maze = {
        {false, false, false, false, false},
        {false, false, false, false, false},
        {false, false, true, false, false},
        {false, false, false, false, false},
        {false, false, false, false, false}};
    assertThat(RandomBackgroundGenerator.isTileInDirection(location, direction, maze)).isEqualTo(result);
  }

  private static Stream<Arguments> isTileInDirectionGeneralStream() {
    return Stream.of(
        Arguments.of(new Point(0, 0), 0, false),
        Arguments.of(new Point(1, 0), 0, false),
        Arguments.of(new Point(2, 0), 0, false),
        Arguments.of(new Point(3, 0), 0, true),
        Arguments.of(new Point(4, 0), 0, true),

        Arguments.of(new Point(0, 1), 0, false),
        Arguments.of(new Point(1, 1), 0, false),
        Arguments.of(new Point(2, 1), 0, false),
        Arguments.of(new Point(3, 1), 0, true),
        Arguments.of(new Point(4, 1), 0, true),

        Arguments.of(new Point(0, 2), 0, true),
        Arguments.of(new Point(1, 2), 0, false),
        Arguments.of(new Point(2, 2), 0, false),
        Arguments.of(new Point(3, 2), 0, true),
        Arguments.of(new Point(4, 2), 0, true),

        Arguments.of(new Point(0, 3), 0, false),
        Arguments.of(new Point(1, 3), 0, false),
        Arguments.of(new Point(2, 3), 0, false),
        Arguments.of(new Point(3, 3), 0, true),
        Arguments.of(new Point(4, 3), 0, true),

        Arguments.of(new Point(0, 4), 0, false),
        Arguments.of(new Point(1, 4), 0, false),
        Arguments.of(new Point(2, 4), 0, false),
        Arguments.of(new Point(3, 4), 0, true),
        Arguments.of(new Point(4, 4), 0, true),


        Arguments.of(new Point(0, 0), 90, false),
        Arguments.of(new Point(1, 0), 90, false),
        Arguments.of(new Point(2, 0), 90, true),
        Arguments.of(new Point(3, 0), 90, false),
        Arguments.of(new Point(4, 0), 90, false),

        Arguments.of(new Point(0, 1), 90, false),
        Arguments.of(new Point(1, 1), 90, false),
        Arguments.of(new Point(2, 1), 90, false),
        Arguments.of(new Point(3, 1), 90, false),
        Arguments.of(new Point(4, 1), 90, false),

        Arguments.of(new Point(0, 2), 90, false),
        Arguments.of(new Point(1, 2), 90, false),
        Arguments.of(new Point(2, 2), 90, false),
        Arguments.of(new Point(3, 2), 90, false),
        Arguments.of(new Point(4, 2), 90, false),

        Arguments.of(new Point(0, 3), 90, true),
        Arguments.of(new Point(1, 3), 90, true),
        Arguments.of(new Point(2, 3), 90, true),
        Arguments.of(new Point(3, 3), 90, true),
        Arguments.of(new Point(4, 3), 90, true),

        Arguments.of(new Point(0, 4), 90, true),
        Arguments.of(new Point(1, 4), 90, true),
        Arguments.of(new Point(2, 4), 90, true),
        Arguments.of(new Point(3, 4), 90, true),
        Arguments.of(new Point(4, 4), 90, true),


        Arguments.of(new Point(0, 0), 180, true),
        Arguments.of(new Point(1, 0), 180, true),
        Arguments.of(new Point(2, 0), 180, false),
        Arguments.of(new Point(3, 0), 180, false),
        Arguments.of(new Point(4, 0), 180, false),

        Arguments.of(new Point(0, 1), 180, true),
        Arguments.of(new Point(1, 1), 180, true),
        Arguments.of(new Point(2, 1), 180, false),
        Arguments.of(new Point(3, 1), 180, false),
        Arguments.of(new Point(4, 1), 180, false),

        Arguments.of(new Point(0, 2), 180, true),
        Arguments.of(new Point(1, 2), 180, true),
        Arguments.of(new Point(2, 2), 180, false),
        Arguments.of(new Point(3, 2), 180, false),
        Arguments.of(new Point(4, 2), 180, true),

        Arguments.of(new Point(0, 3), 180, true),
        Arguments.of(new Point(1, 3), 180, true),
        Arguments.of(new Point(2, 3), 180, false),
        Arguments.of(new Point(3, 3), 180, false),
        Arguments.of(new Point(4, 3), 180, false),

        Arguments.of(new Point(0, 4), 180, true),
        Arguments.of(new Point(1, 4), 180, true),
        Arguments.of(new Point(2, 4), 180, false),
        Arguments.of(new Point(3, 4), 180, false),
        Arguments.of(new Point(4, 4), 180, false),


        Arguments.of(new Point(0, 0), 270, true),
        Arguments.of(new Point(1, 0), 270, true),
        Arguments.of(new Point(2, 0), 270, true),
        Arguments.of(new Point(3, 0), 270, true),
        Arguments.of(new Point(4, 0), 270, true),

        Arguments.of(new Point(0, 1), 270, true),
        Arguments.of(new Point(1, 1), 270, true),
        Arguments.of(new Point(2, 1), 270, true),
        Arguments.of(new Point(3, 1), 270, true),
        Arguments.of(new Point(4, 1), 270, true),

        Arguments.of(new Point(0, 2), 270, false),
        Arguments.of(new Point(1, 2), 270, false),
        Arguments.of(new Point(2, 2), 270, false),
        Arguments.of(new Point(3, 2), 270, false),
        Arguments.of(new Point(4, 2), 270, false),

        Arguments.of(new Point(0, 3), 270, false),
        Arguments.of(new Point(1, 3), 270, false),
        Arguments.of(new Point(2, 3), 270, false),
        Arguments.of(new Point(3, 3), 270, false),
        Arguments.of(new Point(4, 3), 270, false),

        Arguments.of(new Point(0, 4), 270, false),
        Arguments.of(new Point(1, 4), 270, false),
        Arguments.of(new Point(2, 4), 270, true),
        Arguments.of(new Point(3, 4), 270, false),
        Arguments.of(new Point(4, 4), 270, false)
        );
  }
}