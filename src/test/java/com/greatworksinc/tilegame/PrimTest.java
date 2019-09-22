package com.greatworksinc.tilegame;

import com.google.common.testing.EqualsTester;
import com.greatworksinc.tilegame.model.MazeTile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Random;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class PrimTest extends Mockito {

  private @Mock Random random;
  private MazeTile[][] maze;

  @BeforeEach
  void setUp() {
    maze = new MazeTile[][] {{MazeTile.FLOOR, MazeTile.FLOOR, MazeTile.FLOOR}, {MazeTile.FLOOR, MazeTile.FLOOR, MazeTile.FLOOR}, {MazeTile.FLOOR, MazeTile.FLOOR, MazeTile.FLOOR}, {MazeTile.FLOOR, MazeTile.FLOOR, MazeTile.FLOOR}};
  }

  @Test
  void isPointInList_4x1() {
    assertThat(Prim.isPointInList(maze, new Prim.Point(4, 1))).isFalse();
  }

  @Test
  void isPointInList_negative1() {
    assertThat(Prim.isPointInList(maze, new Prim.Point(-1, 1))).isFalse();
  }

  @Test
  void isPointInList_1x3() {
    assertThat(Prim.isPointInList(maze, new Prim.Point(1, 3))).isFalse();
  }

  @Test
  void isPointInList_2x2() {
    assertThat(Prim.isPointInList(maze, new Prim.Point(2, 2))).isTrue();
  }

  @Test
  void pointEquality() {
    new EqualsTester().addEqualityGroup(new Prim.Point(1, 1), new Prim.Point(1, 1))
        .addEqualityGroup(new Prim.Point(1, 2))
        .addEqualityGroup(new Prim.Point(2, 1));
  }

  @Test
  void opposite_nullParent_error() {
    assertThrows(IllegalStateException.class, () -> new Prim.Point(1, 1).opposite());
  }

  @Test
  void opposite_samePoints_null() {
    Prim.Point parentPoint = new Prim.Point(1, 1);
    Prim.Point childPoint = new Prim.Point(1, 1, parentPoint);
    assertThat(childPoint.opposite()).isNull();
  }

  @Test
  void generateMaze_noDimensionalVariance_success() {
    doReturn(3).when(random).nextInt(4);
    doReturn(2).when(random).nextInt(5);
    MazeTile[][] generatedMaze = new Prim(4, 4, 5, 5, random).generateMaze();
    assertThat(generatedMaze.length).isEqualTo(4);
    for (MazeTile[] row : generatedMaze) {
      assertThat(row.length).isEqualTo(5);
    }
    verifyZeroInteractions(random);
  }

  @Test
  void generateMaze_rowMaxLessThanMin_error() {
    assertThrows(IllegalArgumentException.class,
        () -> new Prim(4, 3, 5, 5, random).generateMaze());
    verifyZeroInteractions(random);
  }

  @Test
  void generateMaze_colMaxLessThanMin_error() {
    assertThrows(IllegalArgumentException.class,
        () -> new Prim(4, 4, 6, 5, random).generateMaze());
    verifyZeroInteractions(random);
  }
}