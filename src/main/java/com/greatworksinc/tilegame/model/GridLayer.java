package com.greatworksinc.tilegame.model;

import com.google.common.collect.ImmutableMap;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Scanner;

public class GridLayer {
  private final GridSize gridSize;
  private final ImmutableMap<GridLocation, Integer> gidByLocation;

  public GridLayer(URL url) {
    ImmutableMap.Builder<GridLocation, Integer> output = ImmutableMap.builder();
    Scanner scanner = null;
    try {
      scanner = new Scanner(Paths.get(url.toURI()));
      scanner.useDelimiter(",");
    } catch (URISyntaxException | IOException e) {
      throw new RuntimeException(e);
    }
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

  public ImmutableMap<GridLocation, Integer> getGidByLocation() {
    return gidByLocation;
  }
}
