package com.greatworksinc.tilegame.model;

public class GridSize {
  private final int numOfRows;
  private final int numOfCols;

  public GridSize(int numOfRows, int numOfCols) {
    this.numOfRows = numOfRows;
    this.numOfCols = numOfCols;
  }

  public int getNumOfRows() {
    return numOfRows;
  }

  public int getNumOfCols() {
    return numOfCols;
  }
}
