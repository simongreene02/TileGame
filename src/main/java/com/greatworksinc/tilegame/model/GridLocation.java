package com.greatworksinc.tilegame.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.Objects;

public class GridLocation {
  private final int row;
  private final int col;

  public GridLocation(@JsonProperty("row") int row, @JsonProperty("col") int col) {
    this.row = row;
    this.col = col;
  }

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GridLocation that = (GridLocation) o;
    return row == that.row &&
        col == that.col;
  }

  @Override
  public int hashCode() {

    return Objects.hash(row, col);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("row", row)
        .add("col", col)
        .toString();
  }

  public static GridLocation of(int row, int col) {
    return new GridLocation(row, col);
  }
}
