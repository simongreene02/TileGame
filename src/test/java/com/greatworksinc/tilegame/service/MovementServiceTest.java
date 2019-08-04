package com.greatworksinc.tilegame.service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.greatworksinc.tilegame.model.CharacterState;
import com.greatworksinc.tilegame.model.GridDataSource;
import com.greatworksinc.tilegame.model.GridLocation;
import com.greatworksinc.tilegame.model.GridSize;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static com.google.common.truth.Truth.assertThat;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class MovementServiceTest extends Mockito {

  private static final ImmutableSet<Integer> INACCESSIBLE_SPRITES = ImmutableSet.of(75);
  private static final GridSize GRID_SIZE = GridSize.of(10, 10);

  private @Mock GridDataSource gridDataSource;

  private CharacterState player;
  private MovementService movementService;

  @BeforeEach
  void setUp() {
    player = new CharacterState(GridLocation.of(0, 0));
    doReturn(createGridData(GRID_SIZE, 15, GridLocation.of(2, 2), 75)).when(gridDataSource).getDataAsMap(0);
    movementService = new MovementService(INACCESSIBLE_SPRITES, gridDataSource, GRID_SIZE);
  }

  @ParameterizedTest
  @MethodSource("moveLocationTestStream")
  void move_locationTest(GridLocation initLocation, int keyCode, GridLocation finalLocation, boolean expectedResult) {
    player.setPosition(initLocation);
    assertThat(movementService.move(player, keyCode, 0)).isEqualTo(expectedResult);
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

  private static ImmutableMap<GridLocation, Integer> createGridData(GridSize gridSize, Integer value, GridLocation impassableSquare, Integer impassableValue) {
    Map<GridLocation, Integer> gridData = new HashMap<>();
    for (int row = 0; row < gridSize.getNumOfRows(); row++) {
      for (int col = 0; col < gridSize.getNumOfCols(); col++) {
          gridData.put(GridLocation.of(row, col), value);
      }
    }
    gridData.put(impassableSquare, impassableValue);
    return ImmutableMap.copyOf(gridData);
  }
}