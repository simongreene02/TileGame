package com.greatworksinc.tilegame.tools;

import com.greatworksinc.tilegame.model.GridDataSource;
import com.greatworksinc.tilegame.model.GridSize;

import javax.inject.Inject;

public class ForegroundGenerator implements GridDataSource {

  private GridSize gridSize;

  @Inject
  public ForegroundGenerator(GridSize gridSize) {
    this.gridSize = gridSize;
  }

  @Override
  public String getDataAsString() {
    StringBuilder outString = new StringBuilder();
    outString.append("58,");
    for (int i = 0; i < gridSize.getNumOfRows() - 1; i++) {
      outString.append("0,");
    }
    for (int i = 0; i < gridSize.getNumOfCols() - 2; i++) {
      outString.append('\n');
      for (int j = 0; j < gridSize.getNumOfRows(); j++) {
        outString.append("0,");
      }
    }
    outString.append('\n');
    for (int i = 0; i < gridSize.getNumOfRows() - 1; i++) {
      outString.append("0,");
    }
    outString.append("57");

    return outString.toString();
  }
}
