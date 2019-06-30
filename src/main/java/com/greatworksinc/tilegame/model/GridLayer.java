package com.greatworksinc.tilegame.model;

import com.google.common.collect.ImmutableMap;
import com.google.inject.assistedinject.Assisted;

import javax.inject.Inject;

public class GridLayer {
  private final GridSize gridSize;
  private final ImmutableMap<GridLocation, Integer> gidByLocation;

  @Inject
  public GridLayer(@Assisted GridDataSource gridDataSource, GridSize gridSize) {
    this.gidByLocation = gridDataSource.getDataAsMap(0);
    this.gridSize = gridSize;
  }

  public GridSize getGridSize() {
    return gridSize;
  }

  public int getGidByLocation(GridLocation location) {
    Integer output = gidByLocation.get(location);
    if (output == null) {
      throw new IllegalArgumentException("Location must exist within grid.");
    }
    return output;
  }
}
