package com.greatworksinc.tilegame.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.greatworksinc.tilegame.annotations.Inaccessible;
import com.greatworksinc.tilegame.annotations.MazeBackground;
import com.greatworksinc.tilegame.model.CharacterState;
import com.greatworksinc.tilegame.model.Direction;
import com.greatworksinc.tilegame.model.GridLocation;
import com.greatworksinc.tilegame.model.GridSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

import java.util.HashMap;

import static java.awt.event.KeyEvent.*;

public class MovementService {

  private static final Logger log = LoggerFactory.getLogger(MovementService.class);
  private final GridSize gridSize;
  private final ImmutableSet<Integer> inaccessibleSprites;
  private final ImmutableMap<GridLocation, Integer> tileMap;

  @Inject
  public MovementService(GridSize gridSize, @Inaccessible ImmutableSet<Integer> inaccessibleSprites, @MazeBackground ImmutableMap<GridLocation, Integer> tileMap) {
    this.gridSize = gridSize;
    this.inaccessibleSprites = inaccessibleSprites;
    this.tileMap = tileMap;
  }

  public boolean move(CharacterState characterState, int keyCode) {
    switch (keyCode) {
      case VK_UP:
        handlePosture(characterState, Direction.NORTH);
        if (characterState.getPosition().y > 0 && !inaccessibleSprites.contains(tileMap.get(GridLocation.of(characterState.getPosition().y-1, characterState.getPosition().x)))) {
          characterState.getPosition().y--;
          return true;
        } else {
          return false;
        }
      case VK_DOWN:
        handlePosture(characterState, Direction.SOUTH);
        if (characterState.getPosition().y < gridSize.getNumOfRows() - 1 && !inaccessibleSprites.contains(tileMap.get(GridLocation.of(characterState.getPosition().y+1, characterState.getPosition().x)))) {
          characterState.getPosition().y++;
          return true;
        } else {
          return false;
        }
      case VK_LEFT:
        handlePosture(characterState, Direction.WEST);
        if (characterState.getPosition().x > 0 && !inaccessibleSprites.contains(tileMap.get(GridLocation.of(characterState.getPosition().y, characterState.getPosition().x-1)))) {
          characterState.getPosition().x--;
          return true;
        } else {
          return false;
        }
      case VK_RIGHT:
        handlePosture(characterState, Direction.EAST);
        if (characterState.getPosition().x < gridSize.getNumOfCols() - 1 && !inaccessibleSprites.contains(tileMap.get(GridLocation.of(characterState.getPosition().y, characterState.getPosition().x+1)))) {
          characterState.getPosition().x++;
          return true;
        } else {
          return false;
        }
      default:
        log.info("{} keycode was pressed", keyCode);
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
