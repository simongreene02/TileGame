package com.greatworksinc.tilegame.tools;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.greatworksinc.tilegame.model.GridDataSource;
import com.greatworksinc.tilegame.model.GridLocation;
import com.greatworksinc.tilegame.util.MoreResources;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Scanner;

@Singleton
public class BackgroundMazeReader implements GridDataSource {

  /**
   * Generated level used currently. Is not final and can be mutated by the function generateNewMap
   */
  private ImmutableList<ImmutableMap<GridLocation, Integer>> generatedMazes;

  @Inject
  public BackgroundMazeReader() {
    ImmutableMap.Builder<GridLocation, Integer> output = ImmutableMap.builder();
    Scanner scanner;
    try {
      scanner = new Scanner(Paths.get(MoreResources.getResource("maze.csv").toURI()));
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
    generatedMazes = ImmutableList.of(output.build());
  }

  @Override
  public ImmutableMap<GridLocation, Integer> getDataAsMap() {
    return generatedMazes.get(0);
  }

}
