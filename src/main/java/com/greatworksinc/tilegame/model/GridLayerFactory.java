package com.greatworksinc.tilegame.model;

import com.google.common.collect.ImmutableMap;

public interface GridLayerFactory {
  GridLayer createBackgroundGridLayer(ImmutableMap<GridLocation, Integer> gidByLocation);
}
