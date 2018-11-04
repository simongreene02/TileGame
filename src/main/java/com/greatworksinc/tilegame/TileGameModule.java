package com.greatworksinc.tilegame;

import com.google.inject.PrivateModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.greatworksinc.tilegame.gui.MainFrame;
import com.greatworksinc.tilegame.gui.MainPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

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
}
