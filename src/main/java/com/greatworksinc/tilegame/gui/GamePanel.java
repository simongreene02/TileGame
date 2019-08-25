package com.greatworksinc.tilegame.gui;

import com.greatworksinc.tilegame.annotations.*;
import com.greatworksinc.tilegame.model.*;
import com.greatworksinc.tilegame.service.MovementService;
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
  private GridSize gridSize;
  private GridLayerFactory backgroundLayerFactory;
  private GridLayer backgroundLayer;
  private Staircases staircaseLocations;
  private final int maxLevel;
  private int level; //Zero-base
  private GridDataSource backgroundGenerator;


  @Inject
  public GamePanel(@Maze TileLoader mazeTileLoader,
                   @Castle TileLoader castleTileLoader,
                   @CharacterSprite TileLoader characterTileLoader,
                   MovementService movementService,
                   GridLayerFactory backgroundLayerFactory,
                   @MazeBackground GridDataSource backgroundGenerator,
                   @MaxLevel int maxLevel) {
    this.mazeTileLoader = mazeTileLoader;
    this.castleTileLoader = castleTileLoader;
    this.characterTileLoader = characterTileLoader;
    this.movementService = movementService;
    this.backgroundLayerFactory = backgroundLayerFactory;
    keyListener = new GameKeyListener();
    super.addKeyListener(keyListener);
    player = new CharacterState(backgroundGenerator.getStartingLocation(level));
    this.maxLevel = maxLevel;
    this.backgroundLayer = backgroundLayerFactory.createBackgroundGridLayer(backgroundGenerator.getDataAsMap(level));
    this.backgroundGenerator = backgroundGenerator;
    this.staircaseLocations = backgroundGenerator.getStaircases(level);
    this.gridSize = backgroundGenerator.getSize(level);
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
        GridLocation downStair = staircaseLocations.getDownStair();
        GridLocation upStair = staircaseLocations.getUpStair();
        drawSprite(g, mazeTileLoader.getTile(backgroundTile), row, col);
        if (downStair.getRow() == row && downStair.getCol() == col) {
          drawSprite(g, mazeTileLoader.getTile(MazeTile.END_POS.getGid()), row, col);
        } else if (upStair.getRow() == row && upStair.getCol() == col) {
          drawSprite(g, mazeTileLoader.getTile(MazeTile.START_POS.getGid()), row, col);
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
      movementService.move(player, keyEvent.getKeyCode(), level);
      repaint();
      checkExitCondition();
    }
  }

  private void checkExitCondition() {
    GridLocation playerLocation = player.getPosition();
    GridLocation downStair = staircaseLocations.getDownStair();
    if (playerLocation.equals(downStair)) {
      level++;
      if (level > maxLevel) {
        if (JOptionPane.showConfirmDialog(this, "Exit?") == YES_OPTION) {
          System.exit(0);
        }
      } else {
        backgroundLayer = backgroundLayerFactory.createBackgroundGridLayer(backgroundGenerator.getDataAsMap(level));
        staircaseLocations = backgroundGenerator.getStaircases(level);
        GridLocation upStair = staircaseLocations.getUpStair();
        player.setPosition(GridLocation.of(upStair.getRow(), upStair.getCol()));
        gridSize = backgroundGenerator.getSize(level);
      }
    }
  }

  public GridSize getGridSize() {
    return gridSize;
  }
}
