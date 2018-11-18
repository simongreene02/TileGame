package com.greatworksinc.tilegame.gui;

import com.greatworksinc.tilegame.util.MoreResources;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

public class GamePanel extends Abstract2DPanel {

  @Override
  protected void paintComponent(Graphics2D g) {
    URL tileUrl = MoreResources.getResource("Castle2_Layer1.csv");
    Scanner scanner = null;
    try {
      scanner = new Scanner(new File(tileUrl.toString()));
      scanner.useDelimiter(",");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    int row = 0;
    int col = 0;

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
