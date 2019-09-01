package com.greatworksinc.tilegame.tools;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.greatworksinc.tilegame.annotations.FileTemplate;
import com.greatworksinc.tilegame.annotations.MaxLevel;
import com.greatworksinc.tilegame.annotations.StairFileTemplate;
import com.greatworksinc.tilegame.model.GridDataSource;
import com.greatworksinc.tilegame.model.GridLocation;
import com.greatworksinc.tilegame.model.GridSize;
import com.greatworksinc.tilegame.model.Staircases;
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
  private final ImmutableList<ImmutableMap<GridLocation, Integer>> generatedMazes;
  private final ImmutableList<Staircases> staircaseLocations;
  private final ImmutableList<GridSize> gridSizes;

  @Inject
  public BackgroundMazeReader(@FileTemplate String fileTemplate, @StairFileTemplate String stairFileTemplate, @MaxLevel int maxLevel) {
    ImmutableList.Builder<ImmutableMap<GridLocation, Integer>> generatedMazesBuilder = ImmutableList.builder();
    ImmutableList.Builder<Staircases> staircaseLocationsBuilder = ImmutableList.builder();
    ImmutableList.Builder<GridSize> gridSizesBuilder = ImmutableList.builder();
    for (int level = 0; level <= maxLevel; level++) {
      try {
        LevelData levelData = readFile(Paths.get(
            MoreResources.getResource(String.format(fileTemplate, level)).toURI()));
        generatedMazesBuilder.add(levelData.gidByLocation);
        gridSizesBuilder.add(levelData.gridSize);
        staircaseLocationsBuilder.add(levelData.staircaseData);
      } catch (URISyntaxException | IOException e) {
        throw new RuntimeException(e);
      }
    }
    this.generatedMazes = generatedMazesBuilder.build();
    this.staircaseLocations = staircaseLocationsBuilder.build();
    this.gridSizes = gridSizesBuilder.build();
  }

  @VisibleForTesting
  static LevelData readFile(Path file) throws IOException {
    ImmutableMap.Builder<GridLocation, Integer> gidByLocation = ImmutableMap.builder();
    Staircases staircaseData = new ObjectMapper().readValue(file.toFile(), Staircases.class);
    final int numOfRows;
    final int numOfCols;
    try {
      List<String> lines = Files.readAllLines(file);
      numOfRows = lines.size();
      int colIndex = 0;
      for (int rowIndex = 0; rowIndex < numOfRows; rowIndex++) {
        String row = lines.get(rowIndex);
        Iterable<String> colList = ON_COMMA.split(row);
        colIndex = 0;
        for (String col : colList) {
          gidByLocation.put(new GridLocation(rowIndex, colIndex), Integer.parseInt(col));
          colIndex++;
        }
      }
      numOfCols = colIndex - 1;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return new LevelData(gidByLocation.build(), staircaseData, GridSize.of(numOfRows, numOfCols));
  }

  @Override
  public ImmutableMap<GridLocation, Integer> getDataAsMap(int level) {
    if (level < 0 || level >= generatedMazes.size()) {
      throw new IllegalArgumentException("Value must be between 0 and " + generatedMazes.size() + ".");
    } else {
      return generatedMazes.get(level);
    }
  }

  @Override
  public Staircases getStaircases(int level) {
    if (level < 0 || level >= staircaseLocations.size()) {
      throw new IllegalArgumentException("Value must be between 0 and " + staircaseLocations.size() + ".");
    } else {
      return staircaseLocations.get(level);
    }
  }

  @Override
  public GridLocation getStartingLocation(int level) {
    GridLocation upStair = staircaseLocations.get(level).getUpStair();
    return upStair;
  }

  @Override
  public GridSize getSize(int level) {
    if (level < 0 || level >= gridSizes.size()) {
      throw new IllegalArgumentException("Value must be between 0 and " + gridSizes.size() + ".");
    } else {
      return gridSizes.get(level);
    }
  }

  private static class LevelData {
    private final ImmutableMap<GridLocation, Integer> gidByLocation;
    private final Staircases staircaseData;
    private final GridSize gridSize;

    public LevelData(ImmutableMap<GridLocation, Integer> gidByLocation, Staircases staircaseData, GridSize gridSize) {
      this.gidByLocation = gidByLocation;
      this.staircaseData = staircaseData;
      this.gridSize = gridSize;
    }
  }

}
