package com.greatworksinc.tilegame;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.inject.PrivateModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.greatworksinc.tilegame.annotations.*;
import com.greatworksinc.tilegame.gui.MainFrame;
import com.greatworksinc.tilegame.gui.MainPanel;
import com.greatworksinc.tilegame.model.GridLayer;
import com.greatworksinc.tilegame.model.GridLocation;
import com.greatworksinc.tilegame.model.GridSize;
import com.greatworksinc.tilegame.util.MoreResources;
import com.greatworksinc.tilegame.util.TileLoader;
import com.greatworksinc.tilegame.util.TileLoaderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Scanner;

public class TileGameModule extends PrivateModule {
  private static final Logger log = LoggerFactory.getLogger(TileGameModule.class);
  @Override
  protected void configure() {
    install(new FactoryModuleBuilder().build(TileLoaderFactory.class));
    bind(JPanel.class).to(MainPanel.class).in(Singleton.class);
    bind(JFrame.class).to(MainFrame.class);
    expose(JFrame.class);
  }

  @Provides
  @Singleton
  private LayoutManager provideBorderLayout() {
    log.info("provideBorderLayout");
    return new BorderLayout();
  }

  @Provides
  @Singleton
  @Castle
  private TileLoader provideCastleTiles(@Castle URL url, @Castle Dimension tileSize, TileLoaderFactory tileLoaderFactory) {
    log.info("provideCastleTiles");
    return tileLoaderFactory.createTileLoader(url, tileSize);
  }

  @Provides
  @Singleton
  @CharacterSprite
  private TileLoader provideCharacterTiles(@CharacterSprite URL url, @CharacterSprite Dimension tileSize, TileLoaderFactory tileLoaderFactory) {
    log.info("provideCharacterTiles");
    return tileLoaderFactory.createTileLoader(url, tileSize);
  }

  @Provides
  @Singleton
  @Maze
  private TileLoader provideMazeTiles(@Maze URL url, @Maze Dimension tileSize, TileLoaderFactory tileLoaderFactory) {
    log.info("provideMazeTiles");
    return tileLoaderFactory.createTileLoader(url, tileSize);
  }

  @Provides
  @Singleton
  @Height
  private int provideHeight() {
    log.info("provideHeight");
    return 32;
  }

  @Provides
  @Singleton
  @Width
  private int provideWidth() {
    log.info("provideWidth");
    return 32;
  }

  @Provides
  @Singleton
  @Castle
  private Dimension provideCastleTileSize() {
    return new Dimension(32, 32);
  }

  @Provides
  @Singleton
  @CharacterSprite
  private Dimension provideCharacterTileSize() {
    return new Dimension(16, 16);
  }

  @Provides
  @Singleton
  @Maze
  private Dimension provideMazeTileSize() {
    return new Dimension(16, 16);
  }

  @Provides
  @Singleton
  @Castle
  private URL provideCastleURL() {
    log.info("provideCastleURL");
    return MoreResources.getResource("Castle2.png");
  }

  @Provides
  @Singleton
  @CharacterSprite
  private URL provideCharacterURL() {
    log.info("provideCharacterURL");
    return MoreResources.getResource("characters.png");
  }

  @Provides
  @Singleton
  @Maze
  private URL provideMazeURL() {
    log.info("provideMazeURL");
    return MoreResources.getResource("basictiles.png");
  }

  @Provides
  @Singleton
  private GridSize provideGridSize() {
    return new GridLayer(MoreResources.getResource("Maze_Layer1.csv")).getGridSize();
  }

  @Provides
  @Singleton
  @MazeBackground
  private ImmutableMap<GridLocation, Integer> provideBackgroundTileMap() {
    return new GridLayer(MoreResources.getResource("Maze_Layer1.csv")).getGidByLocation();
  }

  @Provides
  @Singleton
  @MazeForeground
  private ImmutableMap<GridLocation, Integer> provideForegroundTileMap() {
    return new GridLayer(MoreResources.getResource("Maze_Layer2.csv")).getGidByLocation();
  }

  @Provides
  @Singleton
  private ImmutableSet<Integer> provideInaccessibleSpriteIDs() {
    log.info("provideInaccessibleSpriteIDs");
    return ImmutableSet.of(75);
  }
}
