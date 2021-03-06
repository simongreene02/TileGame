package com.greatworksinc.tilegame.util;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.inject.assistedinject.Assisted;
import com.greatworksinc.tilegame.annotations.Height;
import com.greatworksinc.tilegame.annotations.Width;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class TileLoader {
  private final ImmutableList<BufferedImage> tiles;
  private final int numOfTilesPerRow;
  @Inject
  public TileLoader(@Assisted URL tilesURL, @Assisted Dimension tileSize) {
    try {
      BufferedImage bigImg = ImageIO.read(tilesURL);
      numOfTilesPerRow = bigImg.getWidth() / tileSize.width;
      tiles = TileLoader.createTiles(bigImg, tileSize.width, tileSize.height);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public BufferedImage getTile(int tileID) {
    if (tileID < 1 || tileID > tiles.size()) {
      throw new IllegalArgumentException("TileID must be between 1 and " + tiles.size() + ".");
    }
    return tiles.get(tileID-1);
  }

  @VisibleForTesting static ImmutableList<BufferedImage> createTiles(BufferedImage bigImg, int width, int height) {
    ImmutableList.Builder<BufferedImage> tiles = ImmutableList.builder();
    for (int y = 0; y < bigImg.getHeight(); y += height) {
      for (int x = 0; x < bigImg.getWidth(); x += width) {
        tiles.add(bigImg.getSubimage(x, y, width, height));
      }
    }
    return tiles.build();
  }
}
