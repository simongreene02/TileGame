package com.greatworksinc.tilegame.model;

import com.google.common.collect.ImmutableMap;
import com.google.inject.assistedinject.Assisted;
import com.greatworksinc.tilegame.annotations.MazeBackground;

import javax.inject.Inject;
import java.util.Scanner;

public class GridLayer {
  private final GridSize gridSize;
  private final ImmutableMap<GridLocation, Integer> gidByLocation;

  @Inject
  public GridLayer(@Assisted GridDataSource source) {
    ImmutableMap.Builder<GridLocation, Integer> output = ImmutableMap.builder();
    Scanner scanner = new Scanner(source.getDataAsString());
    scanner.useDelimiter(",");
    int row = 0;
    int col = 0;
    while (scanner.hasNext()) {
      String next = scanner.next();
      if (next.indexOf('\n') != -1) {
        row++;
        col = 0;
        next = next.substring(1);
      }
      output.put(new GridLocation(row, col), Integer.parseInt(next));
      col++;
    }
    gridSize = new GridSize(row+1, col);
    gidByLocation = output.build();
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
