package com.greatworksinc.tilegame.service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.greatworksinc.tilegame.annotations.Inaccessible;
import com.greatworksinc.tilegame.annotations.MazeBackground;
import com.greatworksinc.tilegame.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import static java.awt.event.KeyEvent.*;

public class MovementService {

  private static final Logger log = LoggerFactory.getLogger(MovementService.class);
  private final ImmutableSet<Integer> inaccessibleSprites;
  private final GridDataSource tileLayerSource;

  @Inject
  public MovementService(@Inaccessible ImmutableSet<Integer> inaccessibleSprites, @MazeBackground GridDataSource tileLayerSource) {
    this.inaccessibleSprites = inaccessibleSprites;
    this.tileLayerSource = tileLayerSource;
  }

  public boolean move(CharacterState characterState, int keyCode, int level) {
    ImmutableMap<GridLocation, Integer> tileLayer = tileLayerSource.getDataAsMap(level);
    GridSize gridSize = tileLayerSource.getSize(level);

    GridLocation finalLocation = null;
    switch (keyCode) {
      case VK_UP:
        handlePosture(characterState, Direction.NORTH);
        finalLocation = GridLocation.of(characterState.getPosition().getRow()-1, characterState.getPosition().getCol());
        break;
      case VK_DOWN:
        handlePosture(characterState, Direction.SOUTH);
        finalLocation = GridLocation.of(characterState.getPosition().getRow()+1, characterState.getPosition().getCol());
        break;
      case VK_LEFT:
        handlePosture(characterState, Direction.WEST);
        finalLocation = GridLocation.of(characterState.getPosition().getRow(), characterState.getPosition().getCol()-1);
        break;
      case VK_RIGHT:
        handlePosture(characterState, Direction.EAST);
        finalLocation = GridLocation.of(characterState.getPosition().getRow(), characterState.getPosition().getCol()+1);
        break;
      default:
        log.info("{} keycode was pressed", keyCode);
        return false;
    }

    if (finalLocation.getRow() >= 0 && finalLocation.getRow() < gridSize.getNumOfRows()
        && finalLocation.getCol() >= 0 && finalLocation.getCol() < gridSize.getNumOfCols()
        && !inaccessibleSprites.contains(tileLayer.get(finalLocation))) {
      characterState.setPosition(finalLocation);
      return true;
    } else {
      return false;
    }
  }

  private void handlePosture(CharacterState characterState, Direction direction) {
    if (characterState.getDirection() == direction) {
      characterState.incrementPosture();
    } else {
      characterState.setDirection(direction);
      characterState.resetPosture();
    }
  }
}
