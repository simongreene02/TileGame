package com.greatworksinc.tilegame.model;

import com.greatworksinc.tilegame.annotations.MazeBackground;
import com.greatworksinc.tilegame.annotations.MazeForeground;

public interface GridLayerFactory {
  GridLayer createBackgroundGridLayer(GridDataSource gridDataSource);
  GridLayer createForegroundGridLayer(GridDataSource gridDataSource);
}
