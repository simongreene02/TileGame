package com.greatworksinc.tilegame.gui;

import com.greatworksinc.tilegame.util.MoreResources;
import com.greatworksinc.tilegame.util.TileLoader;

import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Scanner;

public class GamePanel extends Abstract2DPanel {

  private final TileLoader tileLoader;

  @Inject
  public GamePanel(TileLoader tileLoader) {
    this.tileLoader = tileLoader;
  }

  @Override
  protected void paintComponent(Graphics2D g) {
    URL tileUrl = MoreResources.getResource("Castle2_Layer1.csv");
    Scanner scanner = null;
    try {
      scanner = new Scanner(Paths.get(tileUrl.toURI()));
      scanner.useDelimiter(",");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (URISyntaxException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    int row = 0;
    int col = 0;
    while (scanner.hasNext()) {
      String next = scanner.next();
      if (next.indexOf('\n') != -1) {
        row++;
        col = 0;
        next = next.substring(1);
        System.out.println();
      }
      drawSprite(g, tileLoader.getTile(Integer.parseInt(next)), row, col);
      col++;
      System.out.print(next + " ");
    }
  }

  private void drawSprite(Graphics2D g, BufferedImage sprite, int row, int col) {
    g.drawImage(sprite,
        col * 32,
        row * 32,
        32,
        32,
        this);
  }
}
