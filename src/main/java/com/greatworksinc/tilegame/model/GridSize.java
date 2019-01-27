package com.greatworksinc.tilegame.model;

import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GridSize gridSize = (GridSize) o;
    return numOfRows == gridSize.numOfRows &&
        numOfCols == gridSize.numOfCols;
  }

  @Override
  public int hashCode() {

    return Objects.hash(numOfRows, numOfCols);
  }

  public static GridSize of(int numOfRows, int numOfCols) {
    return new GridSize(numOfRows, numOfCols);
  }
}
