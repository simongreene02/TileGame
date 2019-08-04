package com.greatworksinc.tilegame.model;

public enum MazeTile {
  FLOOR('-', 15),
  WALL('+', 75),
  START_POS('S', 58),
  END_POS('E', 57);
  private char tile;
  private int gid;
  MazeTile(char tile, int gid) {
    this.tile = tile;
    this.gid = gid;
  }

  public char getTile() {
    return tile;
  }

  public int getGid() {
    return gid;
  }
}
