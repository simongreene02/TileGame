package com.greatworksinc.tilegame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.greatworksinc.tilegame.Prim.isPointInList;

import static com.google.common.truth.Truth.assertThat;


class PrimTest {

  private char[][] maze;

  @BeforeEach
  void setUp() {
    maze = new char[][] {{'.', '.', '.'}, {'.', '.', '.'}, {'.', '.', '.'}, {'.', '.', '.'}};
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