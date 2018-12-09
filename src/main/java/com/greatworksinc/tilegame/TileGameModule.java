package com.greatworksinc.tilegame;

import com.google.common.collect.ImmutableList;
import com.google.inject.PrivateModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.greatworksinc.tilegame.annotations.Castle;
import com.greatworksinc.tilegame.annotations.Character;
import com.greatworksinc.tilegame.annotations.Height;
import com.greatworksinc.tilegame.annotations.Width;
import com.greatworksinc.tilegame.gui.MainFrame;
import com.greatworksinc.tilegame.gui.MainPanel;
import com.greatworksinc.tilegame.model.GridLocation;
import com.greatworksinc.tilegame.model.GridSize;
import com.greatworksinc.tilegame.util.MoreResources;
import com.greatworksinc.tilegame.util.TileLoader;
import com.greatworksinc.tilegame.util.TileLoaderFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.xml.stream.Location;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
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
  @Character
  private TileLoader provideCharacterTiles(@Character URL url, @Character Dimension tileSize, TileLoaderFactory tileLoaderFactory) {
    log.info("provideCharacterTiles");
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
  @Character
  private Dimension provideCharacterTileSize() {
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
  @Character
  private URL provideCharacterURL() {
    log.info("provideCharacterURL");
    return MoreResources.getResource("characters.png");
  }

  @Provides
  @Singleton
  private GridSize provideGridSize() {
    log.info("provideGridSize");
    URL tileUrl = MoreResources.getResource("Castle2_Layer1.csv");
    Scanner scanner = null;
    try {
      scanner = new Scanner(Paths.get(tileUrl.toURI()));
      scanner.useDelimiter(",");
    } catch (URISyntaxException | IOException e) {
      throw new RuntimeException(e);
    }
    int row = 1;
    int col = 0;
    while (scanner.hasNext()) {
      String next = scanner.next();
      if (next.indexOf('\n') != -1) {
        row++;
        col = 0;
        next = next.substring(1);
      }
      col++;
    }
    log.info("row = {}, col = {}", row, col);
    return new GridSize(row, col);
  }

  @Provides
  @Singleton
  private HashMap<GridLocation, Integer> provideTileMap() {
    log.info("provideTileMap");
    HashMap<GridLocation, Integer> output = new HashMap<>();
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
      output.put(new GridLocation(row, col), Integer.parseInt(next));
      col++;
    }
    return output;
  }
}
