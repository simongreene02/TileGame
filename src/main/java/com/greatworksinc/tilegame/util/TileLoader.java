package com.greatworksinc.tilegame.util;

import com.google.common.collect.ImmutableList;
import com.greatworksinc.tilegame.annotations.Height;
import com.greatworksinc.tilegame.annotations.Width;

import javax.imageio.ImageIO;
import javax.inject.Inject;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class TileLoader {
  private final ImmutableList<BufferedImage> tiles;
  private final int numOfTilesPerRow;
  @Inject
  public TileLoader(URL tilesURL, @Width int width, @Height int height) {
    try {
      BufferedImage bigImg = ImageIO.read(tilesURL);
      numOfTilesPerRow = bigImg.getWidth() / width;
      tiles = TileLoader.createTiles(bigImg, width, height);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public ImmutableList<BufferedImage> getTiles() {
    return tiles;
  }

  public BufferedImage getTile(int tileID) {
    if (tileID < 1 || tileID > tiles.size()) {
      throw new IllegalArgumentException("TileID must be between 1 and " + tiles.size() + ".");
    }
    return tiles.get(tileID-1);
  }

  private static ImmutableList<BufferedImage> createTiles(BufferedImage bigImg, int width, int height) {
    ImmutableList.Builder<BufferedImage> tiles = ImmutableList.builder();
    for (int y = 0; y < bigImg.getHeight(); y += height) {
      for (int x = 0; x < bigImg.getWidth(); x += width) {
        tiles.add(bigImg.getSubimage(x, y, width, height));
      }
    }
    return tiles.build();
  }
}
