package com.greatworksinc.tilegame.util;

import java.awt.*;
import java.net.URL;

public interface TileLoaderFactory {
  TileLoader createTileLoader(URL url, Dimension tileSize);
}
