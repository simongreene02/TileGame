package com.greatworksinc.tilegame.tools;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.greatworksinc.tilegame.model.GridDataSource;
import com.greatworksinc.tilegame.model.GridDataSourceGenerator;
import com.greatworksinc.tilegame.model.GridLocation;
import com.greatworksinc.tilegame.model.GridSize;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;
import java.util.Random;

@Singleton
public class BackgroundMazeReader implements GridDataSource {

  private final GridSize gridSize;
  private final Random random;

  /**
   * Generated level used currently. Is not final and can be mutated by the function generateNewMap
   */
  private ImmutableList<ImmutableMap<GridLocation, Integer>> generatedMazes;

  @Inject
  public BackgroundMazeReader(GridSize gridSize, Random random) {
    this.gridSize = gridSize;
    this.random = random;
  }

  @Override
  public ImmutableMap<GridLocation, Integer> getDataAsMap() {
    return generatedMazes.get(0);
  }

}
