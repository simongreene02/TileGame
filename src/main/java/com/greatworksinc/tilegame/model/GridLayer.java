package com.greatworksinc.tilegame.model;

import com.google.common.collect.ImmutableMap;
import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;

public class GridLayer {
  private final ImmutableMap<GridLocation, Integer> gidByLocation;

  @Inject
  public GridLayer(@Assisted ImmutableMap<GridLocation, Integer> gidByLocation) {
    this.gidByLocation = gidByLocation;
  }

  public int getGidByLocation(GridLocation location) {
    Integer output = gidByLocation.get(location);
    if (output == null) {
      throw new IllegalArgumentException("Location must exist within grid.");
    }
    return output;
  }
}
