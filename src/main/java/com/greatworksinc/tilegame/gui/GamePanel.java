package com.greatworksinc.tilegame.gui;

import com.google.common.collect.ImmutableSet;
import com.greatworksinc.tilegame.annotations.*;
import com.greatworksinc.tilegame.model.*;
import com.greatworksinc.tilegame.service.MovementService;
import com.greatworksinc.tilegame.tools.RandomBackgroundGenerator;
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

import static javax.swing.JOptionPane.YES_OPTION;

public class GamePanel extends Abstract2DPanel {

  private static final Logger log = LoggerFactory.getLogger(GamePanel.class);
  private final TileLoader mazeTileLoader;
  private final TileLoader castleTileLoader;
  private final TileLoader characterTileLoader;
  private final MovementService movementService;
  private final CharacterState player;
  private final KeyListener keyListener;
  private final GridSize gridSize;
  private GridLayerFactory backgroundLayerFactory;
  private GridLayer backgroundLayer;
  private final GridLayer foregroundLayer;
  private final ImmutableSet<Integer> exitTileIDs;
  private final int maxLevel;
  private int level;
  private GridDataSourceGenerator backgroundGenerator;


  @Inject
  public GamePanel(@Maze TileLoader mazeTileLoader,
                   @Castle TileLoader castleTileLoader,
                   @CharacterSprite TileLoader characterTileLoader,
                   MovementService movementService,
                   GridSize gridSize,
                   GridLayerFactory backgroundLayerFactory,
                   @MazeBackground GridDataSourceGenerator backgroundGenerator,
                   @MazeForeground GridDataSource foregroundGenerator,
                   ImmutableSet<Integer> exitTileIDs,
                   int maxLevel) {
    this.mazeTileLoader = mazeTileLoader;
    this.castleTileLoader = castleTileLoader;
    this.characterTileLoader = characterTileLoader;
    this.movementService = movementService;
    this.backgroundLayerFactory = backgroundLayerFactory;
    this.foregroundLayer = backgroundLayerFactory.createForegroundGridLayer(foregroundGenerator);
    this.gridSize = gridSize;
    this.exitTileIDs = exitTileIDs;
    keyListener = new GameKeyListener();
    super.addKeyListener(keyListener);
    player = new CharacterState();
    this.maxLevel = maxLevel;
    this.backgroundLayer = backgroundLayerFactory.createBackgroundGridLayer(backgroundGenerator);
    this.backgroundGenerator = backgroundGenerator;
    level = 1;
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
        int backgroundTile = backgroundLayer.getGidByLocation(GridLocation.of(row, col)); //Example getTileIndexAt(int rowIndex, int colIndex)
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
      if (level >= maxLevel) {
        if (JOptionPane.showConfirmDialog(this, "Exit?") == YES_OPTION) {
          System.exit(0);
        }
      } else {
        level++; //Use this variable to generate URL for level CSV file. (return TerrainField.from(MoreResources.getResource("terrain_"+level".csv"));)
        backgroundGenerator.generateNewMap();
        backgroundLayer = backgroundLayerFactory.createBackgroundGridLayer(backgroundGenerator);
        player.setPosition(GridLocation.of(0, 0));
      }
    }
  }
}
