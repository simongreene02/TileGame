package com.greatworksinc.tilegame.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.Objects;

public class StaircaseData {
  //public final GridLocation staircaseLocation;
  private final int row;
  private final int col;
  private final int gid;

  public int getRow() {
    return row;
  }

  public int getCol() {
    return col;
  }

  public int getGid() {
    return gid;
  }

  @JsonCreator
  public StaircaseData(@JsonProperty("row") int row, @JsonProperty("col") int col, @JsonProperty("gid") int gid) {
    this.row = row;
    this.col = col;
    this.gid = gid;
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("row", row)
        .add("col", col)
        .add("gid", gid)
        .toString();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    StaircaseData that = (StaircaseData) o;
    return row == that.row &&
        col == that.col &&
        gid == that.gid;
  }

  @Override
  public int hashCode() {
    return Objects.hash(row, col, gid);
  }
}
