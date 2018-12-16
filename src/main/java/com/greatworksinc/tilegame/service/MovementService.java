package com.greatworksinc.tilegame.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
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
  private final ImmutableList<Integer> inaccessibleSprites;
  private final ImmutableMap<GridLocation, Integer> tileMap;

  @Inject
  public MovementService(GridSize gridSize, ImmutableList<Integer> inaccessibleSprites, ImmutableMap<GridLocation, Integer> tileMap) {
    this.gridSize = gridSize;
    this.inaccessibleSprites = inaccessibleSprites;
    this.tileMap = tileMap;
  }

  public boolean move(CharacterState characterState, int keyCode) {//, GridSize gridSize) {
    //GridSize gridSize = new GridSize(10, 10);
    switch (keyCode) {
      case VK_UP:
        if (characterState.position.y > 0) {
          if (inaccessibleSprites.contains(tileMap.get(GridLocation.of(characterState.position.y-1, characterState.position.x)))) {
            characterState.position.y--;
          }
          if (characterState.direction == Direction.NORTH) {
            characterState.posture++;
          } else {
            characterState.direction = Direction.NORTH;
            characterState.posture = 0;
          }
          return true;
        } else {
          return false;
        }
      case VK_DOWN:
        if (characterState.position.y < gridSize.getNumOfRows() - 1) {
          characterState.position.y++;
          if (characterState.direction == Direction.SOUTH) {
            characterState.posture++;
          } else {
            characterState.direction = Direction.SOUTH;
            characterState.posture = 0;
          }
          return true;
        } else {
          return false;
        }
      case VK_LEFT:
        if (characterState.position.x > 0) {
          characterState.position.x--;
          if (characterState.direction == Direction.WEST) {
            characterState.posture++;
          } else {
            characterState.direction = Direction.WEST;
            characterState.posture = 0;
          }
          return true;
        } else {
          return false;
        }
      case VK_RIGHT:
        if (characterState.position.x < gridSize.getNumOfCols() - 1) {
          characterState.position.x++;
          if (characterState.direction == Direction.EAST) {
            characterState.posture++;
          } else {
            characterState.direction = Direction.EAST;
            characterState.posture = 0;
          }
          return true;
        } else {
          return false;
        }
      default:
        log.info("{} keycode was pressed", keyCode);
        return false;
    }
  }
}
