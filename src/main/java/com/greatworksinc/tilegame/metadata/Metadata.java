package com.greatworksinc.tilegame.metadata;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;
import com.greatworksinc.tilegame.model.GridLocation;

import java.util.Objects;

public class Metadata {
  private final GridLocation location;

  @JsonCreator
  public Metadata(@JsonProperty("location") GridLocation location) {
    this.location = location;
  }

  public GridLocation getLocation() {
    return location;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Metadata metadata = (Metadata) o;
    return Objects.equals(location, metadata.location);
  }

  @Override
  public int hashCode() {
    return Objects.hash(location);
  }

  @Override
  public String toString() {
    return "Metadata{" +
            "location=" + location +
            '}';
  }
}
