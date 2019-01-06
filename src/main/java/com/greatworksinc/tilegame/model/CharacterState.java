package com.greatworksinc.tilegame.model;

import java.awt.*;
import java.util.EnumMap;
import java.util.Map;

public class CharacterState {
  private final Map<Direction, Integer[]> spriteList;
  private Point position;
  private Direction direction;
  private int posture;

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

  public Point getPosition() {
    return position;
  }

  public void setPosition(Point position) {
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
