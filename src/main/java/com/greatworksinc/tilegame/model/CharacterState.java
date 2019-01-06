package com.greatworksinc.tilegame.model;

import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

public class CharacterState {
  private final Map<Direction, Integer[]> spriteList;
  public Point position;
  public Direction direction;
  public int posture;

  public CharacterState() {
    position = new Point(0, 0);
    Integer[] southSpriteList = {49, 50, 51};
    Integer[] westSpriteList = {61, 62, 63};
    Integer[] eastSpriteList = {73, 74, 75};
    Integer[] northSpriteList = {85, 86, 87};
    spriteList = new EnumMap<>(Direction.class);
    spriteList.put(Direction.NORTH, northSpriteList);
    spriteList.put(Direction.SOUTH, southSpriteList);
    spriteList.put(Direction.EAST, eastSpriteList);
    spriteList.put(Direction.WEST, westSpriteList);
    direction = Direction.EAST;
    posture = 0;
  }

  public int getSpriteNumber() {
    Integer[] postureList = spriteList.get(direction);
    return postureList[posture%postureList.length];
  }
}
