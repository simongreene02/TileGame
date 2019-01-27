package com.greatworksinc.tilegame.gui;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.greatworksinc.tilegame.annotations.*;
import com.greatworksinc.tilegame.model.CharacterState;
import com.greatworksinc.tilegame.model.GridLayer;
import com.greatworksinc.tilegame.model.GridLocation;
import com.greatworksinc.tilegame.model.GridSize;
import com.greatworksinc.tilegame.service.MovementService;
import com.greatworksinc.tilegame.util.MoreResources;
import com.greatworksinc.tilegame.util.TileLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Scanner;

import static javax.swing.JOptionPane.YES_OPTION;

public class GamePanel extends Abstract2DPanel {

  private static final Logger log = LoggerFactory.getLogger(GamePanel.class);
  private final TileLoader mazeTileLoader;
  private final TileLoader castleTileLoader;
  private final TileLoader characterTileLoader;
  private final MovementService movementService;
  private CharacterState player;
  private KeyListener keyListener;
  private GridSize gridSize;
  private GridLayer backgroundLayer;
  private GridLayer foregroundLayer;
  private ImmutableSet<Integer> exitTileIDs;

  @Inject
  public GamePanel(@Maze TileLoader mazeTileLoader,
                   @Castle TileLoader castleTileLoader,
                   @CharacterSprite TileLoader characterTileLoader,
                   MovementService movementService,
                   GridSize gridSize,
                   @MazeBackground GridLayer backgroundLayer,
                   @MazeForeground GridLayer foregroundLayer,
                   ImmutableSet<Integer> exitTileIDs) {
    this.mazeTileLoader = mazeTileLoader;
    this.castleTileLoader = castleTileLoader;
    this.characterTileLoader = characterTileLoader;
    this.movementService = movementService;
    this.backgroundLayer = backgroundLayer;
    this.foregroundLayer = foregroundLayer;
    this.gridSize = backgroundLayer.getGridSize();
    this.exitTileIDs = exitTileIDs;
    keyListener = new GameKeyListener();
    super.addKeyListener(keyListener);
    player = new CharacterState();
  }

  @Override
  protected void paintComponent(Graphics2D g) {
    paintTerrain(g);
    paintPlayer(g);
  }

  private void paintPlayer(Graphics2D g) {
    drawSprite(g, characterTileLoader.getTile(player.getSpriteNumber()), player.getPosition().getRow(), player.getPosition().getCol());
  }

  private void paintTerrain(Graphics2D g) {
    for (int row = 0; row < gridSize.getNumOfRows(); row++) {
      for (int col = 0; col < gridSize.getNumOfCols(); col++) {
        int backgroundTile = backgroundLayer.getGidByLocation(GridLocation.of(row, col));
        int foregroundTile = foregroundLayer.getGidByLocation(GridLocation.of(row, col));
        drawSprite(g, mazeTileLoader.getTile(backgroundTile), row, col);
        if (foregroundTile != 0) {
          drawSprite(g, mazeTileLoader.getTile(foregroundTile), row, col);
        }
      }
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
      movementService.move(player, keyEvent.getKeyCode());
      repaint();
      checkExitCondition();
    }
  }

  private void checkExitCondition() {
    GridLocation gridLocation = new GridLocation(player.getPosition().getRow(), player.getPosition().getCol());
    log.info("PlayerForegroundTile: {}", foregroundLayer.getGidByLocation(player.getPosition()));
    if (exitTileIDs.contains(foregroundLayer.getGidByLocation(gridLocation))) {
      if (JOptionPane.showConfirmDialog(this, "Exit?") == YES_OPTION) {
        System.exit(0);
      }
    }
  }
}
