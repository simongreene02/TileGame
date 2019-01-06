package com.greatworksinc.tilegame.gui;

import com.google.common.collect.ImmutableMap;
import com.greatworksinc.tilegame.annotations.*;
import com.greatworksinc.tilegame.model.CharacterState;
import com.greatworksinc.tilegame.model.GridLocation;
import com.greatworksinc.tilegame.model.GridSize;
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
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Scanner;

public class GamePanel extends Abstract2DPanel {

  private static final Logger log = LoggerFactory.getLogger(GamePanel.class);
  private final TileLoader mazeTileLoader;
  private final TileLoader castleTileLoader;
  private final TileLoader characterTileLoader;
  private final MovementService movementService;
  private CharacterState player;
  private KeyListener keyListener;
  private GridSize gridSize;
  private ImmutableMap<GridLocation, Integer> backgroundTiles;
  private ImmutableMap<GridLocation, Integer> foregroundTiles;

  @Inject
  public GamePanel(@Maze TileLoader mazeTileLoader, @Castle TileLoader castleTileLoader, @CharacterSprite TileLoader characterTileLoader, MovementService movementService, GridSize gridSize, @MazeBackground ImmutableMap<GridLocation, Integer> backgroundTiles, @MazeForeground ImmutableMap<GridLocation, Integer> foregroundTiles) {
    this.mazeTileLoader = mazeTileLoader;
    this.castleTileLoader = castleTileLoader;
    this.characterTileLoader = characterTileLoader;
    this.movementService = movementService;
    this.gridSize = gridSize;
    this.backgroundTiles = backgroundTiles;
    this.foregroundTiles = foregroundTiles;
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
    drawSprite(g, characterTileLoader.getTile(player.getSpriteNumber()), player.position.y, player.position.x);
  }

  private void paintTerrain(Graphics2D g) {
    for (int row = 0; row < gridSize.getNumOfRows(); row++) {
      for (int col = 0; col < gridSize.getNumOfCols(); col++) {
        int backgroundTile = backgroundTiles.get(new GridLocation(row, col));
        int foregroundTile = foregroundTiles.get(new GridLocation(row, col));
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
    }
  }
}
