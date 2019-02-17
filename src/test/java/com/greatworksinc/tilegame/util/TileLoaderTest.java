package com.greatworksinc.tilegame.util;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSortedMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.image.BufferedImage;

import static com.google.common.truth.Truth.assertThat;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class TileLoaderTest extends Mockito {

  private @Mock BufferedImage tileSource;
  private @Mock BufferedImage smallImage;

  @BeforeEach
  void setUp() {
  }

  @Test
  void getTile() {
    ImmutableMap.Builder<Integer, BufferedImage> smallImageByNameBuilder = ImmutableSortedMap.naturalOrder();
    int maxRows = 5;
    int maxCols = 10;
    int width = 2;
    int height = 4;
    for (int row = 0; row < maxRows; row++) {
      for (int col = 0; col < maxCols; col++) {
        int key = maxCols*row+col+1;
        BufferedImage smallImg = mock(BufferedImage.class);
        smallImageByNameBuilder.put(key, smallImg);
        doReturn(smallImg).when(tileSource).getSubimage(col*width, row*height, width, height);
      }
    }
    doReturn(maxCols*width).when(tileSource).getWidth();
    doReturn(maxRows*height).when(tileSource).getHeight();
    Iterable<BufferedImage> smallImageByName = smallImageByNameBuilder.build().values();
    ImmutableList<BufferedImage> createdTiles = TileLoader.createTiles(tileSource, width, height);
    assertThat(createdTiles).containsExactlyElementsIn(smallImageByName).inOrder();
  }

  @Test
  void createTiles() {
    doReturn(10).when(tileSource).getWidth();
    doReturn(10).when(tileSource).getHeight();
    for (int i = 0; i < 10; i+=2) {
      for (int j = 0; j < 10; j+=2) {
        doReturn(smallImage).when(tileSource).getSubimage(i, j, 2, 2);
      }
    }
    ImmutableList<BufferedImage> createdTiles = TileLoader.createTiles(tileSource, 2, 2);
    assertThat(createdTiles).hasSize(25);
    for (BufferedImage tile : createdTiles) {
      assertThat(tile).isSameAs(smallImage);
    }
  }
}