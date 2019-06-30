package com.greatworksinc.tilegame.model;

import com.google.common.collect.ImmutableList;

import java.util.EnumMap;
import java.util.Map;

public class CharacterState {
  private final Map<Direction, ImmutableList<Integer>> spriteList;
  private GridLocation position;
  private Direction direction;
  private int posture;

  @Deprecated
  public CharacterState() {
    position = new GridLocation(0, 0);
    spriteList = new EnumMap<>(Direction.class);
    spriteList.put(Direction.NORTH, ImmutableList.of(85, 86, 87));
    spriteList.put(Direction.SOUTH, ImmutableList.of(49, 50, 51));
    spriteList.put(Direction.EAST, ImmutableList.of(73, 74, 75));
    spriteList.put(Direction.WEST, ImmutableList.of(61, 62, 63));
    direction = Direction.EAST;
    posture = 0;
  }

  public CharacterState(GridLocation startingPosition) {
    position = startingPosition;
    spriteList = new EnumMap<>(Direction.class);
    spriteList.put(Direction.NORTH, ImmutableList.of(85, 86, 87));
    spriteList.put(Direction.SOUTH, ImmutableList.of(49, 50, 51));
    spriteList.put(Direction.EAST, ImmutableList.of(73, 74, 75));
    spriteList.put(Direction.WEST, ImmutableList.of(61, 62, 63));
    direction = Direction.EAST;
    posture = 0;
  }

  public int getSpriteNumber() {
    ImmutableList<Integer> postureList = spriteList.get(direction);
    return postureList.get(posture % postureList.size());
  }

  public GridLocation getPosition() {
    return position;
  }

  public void setPosition(GridLocation position) {
    this.position = position;
  }

  public Direction getDirection() {
    return direction;
  }

  public void setDirection(Direction direction) {
    this.direction = direction;
  }

  public int getPosture() {
    return posture;
  }

  public void resetPosture() {
    this.posture = 0;
  }

  public void incrementPosture() {
    this.posture++;
  }
}
