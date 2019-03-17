package com.greatworksinc.tilegame.tools;

import com.google.common.collect.ImmutableMap;
import com.greatworksinc.tilegame.model.GridDataSource;
import com.greatworksinc.tilegame.model.GridLocation;
import com.greatworksinc.tilegame.model.GridSize;

import javax.inject.Inject;

public class ForegroundGenerator implements GridDataSource {

  private GridSize gridSize;

  @Inject
  public ForegroundGenerator(GridSize gridSize) {
    this.gridSize = gridSize;
  }

  @Override
  public ImmutableMap<GridLocation, Integer> getDataAsMap() {
    ImmutableMap.Builder<GridLocation, Integer> outList = ImmutableMap.builder();
    outList.put(GridLocation.of(0, 0), 58);
    for (int j = 1; j < gridSize.getNumOfRows(); j++) {
      outList.put(GridLocation.of(j, 0), 0);
    }
    for (int i = 1; i < gridSize.getNumOfCols() - 1; i++) {
      for (int j = 0; j < gridSize.getNumOfRows(); j++) {
        outList.put(GridLocation.of(j, i), 0);
      }
    }
    for (int j = 0; j < gridSize.getNumOfRows() - 1; j++) {
      outList.put(GridLocation.of(j, gridSize.getNumOfCols() - 1), 0);
    }
    outList.put(GridLocation.of(gridSize.getNumOfRows()-1, gridSize.getNumOfCols()-1), 57);

    return outList.build();
  }
}
