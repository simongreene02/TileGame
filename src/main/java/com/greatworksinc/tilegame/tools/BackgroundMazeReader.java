package com.greatworksinc.tilegame.tools;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.greatworksinc.tilegame.annotations.FileTemplate;
import com.greatworksinc.tilegame.annotations.MaxLevel;
import com.greatworksinc.tilegame.model.GridDataSource;
import com.greatworksinc.tilegame.model.GridLocation;
import com.greatworksinc.tilegame.util.MoreResources;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Singleton
public class BackgroundMazeReader implements GridDataSource {
  private static final Splitter ON_COMMA = Splitter.on(',').trimResults().omitEmptyStrings();

  /**
   * Generated level used currently. Is not final and can be mutated by the function generateNewMap
   */
  private ImmutableList<ImmutableMap<GridLocation, Integer>> generatedMazes;

  @Inject
  public BackgroundMazeReader(@FileTemplate String fileTemplate, @MaxLevel int maxLevel) {
    ImmutableList.Builder<ImmutableMap<GridLocation, Integer>> generatedMazesBuilder = ImmutableList.builder();
    for (int level = 1; level <= maxLevel; level++) {
      try {
        generatedMazesBuilder.add(readFile(Paths.get(
            MoreResources.getResource(String.format(fileTemplate, level)).toURI())));
      } catch (URISyntaxException e) {
        throw new RuntimeException(e);
      }
    }
    generatedMazes = generatedMazesBuilder.build();
  }

  @VisibleForTesting
  static ImmutableMap<GridLocation, Integer> readFile(Path file) {
    ImmutableMap.Builder<GridLocation, Integer> output = ImmutableMap.builder();
    try {
      List<String> lines = Files.readAllLines(file);
      for (int rowIndex = 0; rowIndex < lines.size(); rowIndex++) {
        String row = lines.get(rowIndex);
        Iterable<String> colList = ON_COMMA.split(row);
        int colIndex = 0;
        for (String col : colList) {
          output.put(new GridLocation(rowIndex, colIndex), Integer.parseInt(col));
          colIndex++;
        }
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return output.build();
  }

  @Override
  public ImmutableMap<GridLocation, Integer> getDataAsMap() {
    return generatedMazes.get(0);
  }

}
