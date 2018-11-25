package com.greatworksinc.tilegame.gui;

import com.greatworksinc.tilegame.TileGameModule;
import com.greatworksinc.tilegame.service.MovementService;
import com.greatworksinc.tilegame.util.MoreResources;
import com.greatworksinc.tilegame.util.TileLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Scanner;

import static java.awt.event.KeyEvent.*;

public class GamePanel extends Abstract2DPanel {

  private static final Logger log = LoggerFactory.getLogger(GamePanel.class);
  private final TileLoader tileLoader;
  private final MovementService movementService;
  private final Point playerPosition = new Point(4, 4);
  private KeyListener keyListener;

  @Inject
  public GamePanel(TileLoader tileLoader, MovementService movementService) {
    this.tileLoader = tileLoader;
    this.movementService = movementService;
    keyListener = new GameKeyListener();
    super.addKeyListener(keyListener);
  }

  @Override
  protected void paintComponent(Graphics2D g) {
    paintTerrain(g);
    paintPlayer(g);
  }

  private void paintPlayer(Graphics2D g) {
    drawSprite(g, tileLoader.getTile(234), playerPosition.y, playerPosition.x);
  }

  private void paintTerrain(Graphics2D g) {
    URL tileUrl = MoreResources.getResource("Castle2_Layer1.csv");
    Scanner scanner = null;
    try {
      scanner = new Scanner(Paths.get(tileUrl.toURI()));
      scanner.useDelimiter(",");
    } catch (URISyntaxException | IOException e) {
      throw new RuntimeException(e);
    }
    int row = 0;
    int col = 0;
    while (scanner.hasNext()) {
      String next = scanner.next();
      if (next.indexOf('\n') != -1) {
        row++;
        col = 0;
        next = next.substring(1);
      }
      drawSprite(g, tileLoader.getTile(Integer.parseInt(next)), row, col);
      col++;
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

  private class GameKeyListener extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent keyEvent) {
      movementService.move(playerPosition, keyEvent.getKeyCode());
      repaint();
    }
  }
}
