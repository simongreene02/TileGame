package com.greatworksinc.tilegame.service;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableSet;
import com.greatworksinc.tilegame.model.CharacterState;
import com.greatworksinc.tilegame.model.GridLayer;
import com.greatworksinc.tilegame.model.GridLocation;
import com.greatworksinc.tilegame.util.MoreResources;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MovementServiceTest {

  private static final ImmutableSet<Integer> INACCESSIBLE_SPRITES = ImmutableSet.of(75);

  private CharacterState player;
  private MovementService movementService;

  @BeforeEach
  void setUp() {
    player = new CharacterState();
    movementService = new MovementService(INACCESSIBLE_SPRITES,
        new GridLayer(MoreResources.getResource("Maze_Layer_test.csv")));
  }

  @ParameterizedTest
  @MethodSource("moveLocationTestStream")
  void move_locationTest(GridLocation initLocation, int keyCode, GridLocation finalLocation, boolean expectedResult) {
    player.setPosition(initLocation);
    assertThat(movementService.move(player, keyCode)).isEqualTo(expectedResult);
    assertThat(player.getPosition()).isEqualTo(finalLocation);
  }

  private static Stream<Arguments> moveLocationTestStream() {
    return Stream.of(
        //Moving from the center of the stage.
        Arguments.of(GridLocation.of(5, 5), 37, GridLocation.of(5, 4), true),
        Arguments.of(GridLocation.of(5, 5), 38, GridLocation.of(4, 5), true),
        Arguments.of(GridLocation.of(5, 5), 39, GridLocation.of(5, 6), true),
        Arguments.of(GridLocation.of(5, 5), 40, GridLocation.of(6, 5), true),
        //Moving from the top-left corner.
        Arguments.of(GridLocation.of(0, 0), 37, GridLocation.of(0, 0), false),
        Arguments.of(GridLocation.of(0, 0), 38, GridLocation.of(0, 0), false),
        Arguments.of(GridLocation.of(0, 0), 39, GridLocation.of(0, 1), true),
        Arguments.of(GridLocation.of(0, 0), 40, GridLocation.of(1, 0), true),
        //Moving from the top-right corner.
        Arguments.of(GridLocation.of(0, 9), 37, GridLocation.of(0, 8), true),
        Arguments.of(GridLocation.of(0, 9), 38, GridLocation.of(0, 9), false),
        Arguments.of(GridLocation.of(0, 9), 39, GridLocation.of(0, 9), false),
        Arguments.of(GridLocation.of(0, 9), 40, GridLocation.of(1, 9), true),
        //Moving from the bottom-left corner.
        Arguments.of(GridLocation.of(9, 0), 37, GridLocation.of(9, 0), false),
        Arguments.of(GridLocation.of(9, 0), 38, GridLocation.of(8, 0), true),
        Arguments.of(GridLocation.of(9, 0), 39, GridLocation.of(9, 1), true),
        Arguments.of(GridLocation.of(9, 0), 40, GridLocation.of(9, 0), false),
        //Moving from the bottom-right corner.
        Arguments.of(GridLocation.of(9, 9), 37, GridLocation.of(9, 8), true),
        Arguments.of(GridLocation.of(9, 9), 38, GridLocation.of(8, 9), true),
        Arguments.of(GridLocation.of(9, 9), 39, GridLocation.of(9, 9), false),
        Arguments.of(GridLocation.of(9, 9), 40, GridLocation.of(9, 9), false),
        //Moving from the left of immovable space.
        Arguments.of(GridLocation.of(2, 1), 37, GridLocation.of(2, 0), true),
        Arguments.of(GridLocation.of(2, 1), 38, GridLocation.of(1, 1), true),
        Arguments.of(GridLocation.of(2, 1), 39, GridLocation.of(2, 1), false),
        Arguments.of(GridLocation.of(2, 1), 40, GridLocation.of(3, 1), true),
        //Moving from above immovable space.
        Arguments.of(GridLocation.of(1, 2), 37, GridLocation.of(1, 1), true),
        Arguments.of(GridLocation.of(1, 2), 38, GridLocation.of(0, 2), true),
        Arguments.of(GridLocation.of(1, 2), 39, GridLocation.of(1, 3), true),
        Arguments.of(GridLocation.of(1, 2), 40, GridLocation.of(1, 2), false),
        //Moving from the right of immovable space.
        Arguments.of(GridLocation.of(2, 3), 37, GridLocation.of(2, 3), false),
        Arguments.of(GridLocation.of(2, 3), 38, GridLocation.of(1, 3), true),
        Arguments.of(GridLocation.of(2, 3), 39, GridLocation.of(2, 4), true),
        Arguments.of(GridLocation.of(2, 3), 40, GridLocation.of(3, 3), true),
        //Moving from below immovable space.
        Arguments.of(GridLocation.of(3, 2), 37, GridLocation.of(3, 1), true),
        Arguments.of(GridLocation.of(3, 2), 38, GridLocation.of(3, 2), false),
        Arguments.of(GridLocation.of(3, 2), 39, GridLocation.of(3, 3), true),
        Arguments.of(GridLocation.of(3, 2), 40, GridLocation.of(4, 2), true),
        //Invalid key command.
        Arguments.of(GridLocation.of(5, 5), 36, GridLocation.of(5, 5), false)
    );
  }
}