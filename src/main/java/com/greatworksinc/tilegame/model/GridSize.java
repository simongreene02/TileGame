package com.greatworksinc.tilegame.model;

import static com.greatworksinc.tilegame.util.Preconditions.checkPositiveIntegers;

public class GridSize {
  private final int numOfRows;
  private final int numOfCols;

  public GridSize(int numOfRows, int numOfCols) {
    this.numOfRows = checkPositiveIntegers(numOfRows);
    this.numOfCols = checkPositiveIntegers(numOfCols);
  }

  public int getNumOfRows() {
    return numOfRows;
  }

  public int getNumOfCols() {
    return numOfCols;
  }
}
