package com.greatworksinc.tilegame.model;

import com.google.common.collect.ImmutableMap;

public interface GridDataSource {
  ImmutableMap<GridLocation, Integer> getDataAsMap(int level);
  Staircases getStaircases(int level);

  GridLocation getStartingLocation(int level);
  GridSize getSize(int level);
}


