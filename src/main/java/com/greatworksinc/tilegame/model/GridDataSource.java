package com.greatworksinc.tilegame.model;

import com.google.common.collect.ImmutableMap;

public interface GridDataSource {
  ImmutableMap<GridLocation, Integer> getDataAsMap();
  Staircases getStaircases();
}


