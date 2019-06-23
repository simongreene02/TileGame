package com.greatworksinc.tilegame.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.Objects;

public class Staircases {
  private final StaircaseData upStair;
  private final StaircaseData downStair;

  @JsonCreator
  public Staircases(@JsonProperty("upStair") StaircaseData upStair, @JsonProperty("downStair") StaircaseData downStair) {
    this.upStair = upStair;
    this.downStair = downStair;
  }

  public StaircaseData getUpStair() {
    return upStair;
  }

  public StaircaseData getDownStair() {
    return downStair;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Staircases that = (Staircases) o;
    return Objects.equals(upStair, that.upStair) &&
        Objects.equals(downStair, that.downStair);
  }

  @Override
  public int hashCode() {
    return Objects.hash(upStair, downStair);
  }

  @Override
  public String toString() {
    return MoreObjects.toStringHelper(this)
        .add("upStair", upStair)
        .add("downStair", downStair)
        .toString();
  }
}
