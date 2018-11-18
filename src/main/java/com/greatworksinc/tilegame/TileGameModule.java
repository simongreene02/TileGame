package com.greatworksinc.tilegame;

import com.google.common.collect.ImmutableList;
import com.google.inject.PrivateModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.greatworksinc.tilegame.annotations.Height;
import com.greatworksinc.tilegame.annotations.Width;
import com.greatworksinc.tilegame.gui.MainFrame;
import com.greatworksinc.tilegame.gui.MainPanel;
import com.greatworksinc.tilegame.util.MoreResources;
import com.greatworksinc.tilegame.util.TileLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URL;

public class TileGameModule extends PrivateModule {
  private static final Logger log = LoggerFactory.getLogger(TileGameModule.class);
  @Override
  protected void configure() {
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
  private ImmutableList<BufferedImage> provideTiles(TileLoader tileLoader) {
    log.info("provideTiles");
    return tileLoader.getTiles();
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
  private URL provideURL() {
    log.info("provideURL");
    return MoreResources.getResource("Castle2.png");
  }
}
