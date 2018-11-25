package com.greatworksinc.tilegame.model;

public class GridLocation {
  private final int row;
  private final int col;

  public GridLocation(int row, int col) {
    this.row = row;
    this.col = col;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }
}
