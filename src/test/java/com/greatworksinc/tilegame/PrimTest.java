package com.greatworksinc.tilegame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;


class PrimTest {

  private Prim.MazeTile[][] maze;

  @BeforeEach
  void setUp() {
    maze = new Prim.MazeTile[][] {{Prim.MazeTile.FLOOR, Prim.MazeTile.FLOOR, Prim.MazeTile.FLOOR}, {Prim.MazeTile.FLOOR, Prim.MazeTile.FLOOR, Prim.MazeTile.FLOOR}, {Prim.MazeTile.FLOOR, Prim.MazeTile.FLOOR, Prim.MazeTile.FLOOR}, {Prim.MazeTile.FLOOR, Prim.MazeTile.FLOOR, Prim.MazeTile.FLOOR}};
  }

  @Test
  void isPointInList_4x1() {
    assertThat(Prim.isPointInList(maze, new Prim.Point(4, 1, null))).isFalse();
  }

  @Test
  void isPointInList_negative1() {
    assertThat(Prim.isPointInList(maze, new Prim.Point(-1, 1, null))).isFalse();
  }

  @Test
  void isPointInList_1x3() {
    assertThat(Prim.isPointInList(maze, new Prim.Point(1, 3, null))).isFalse();
  }

  @Test
  void isPointInList_2x2() {
    assertThat(Prim.isPointInList(maze, new Prim.Point(2, 2, null))).isTrue();
  }
}