package com.greatworksinc.tilegame.gui;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

  private static final Logger log = LoggerFactory.getLogger(MainPanel.class);

  @Inject
  public MainPanel(LayoutManager layout, GamePanel panel) {
    setLayout(layout);
    add(panel);
    log.info("init");
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(352, 352);
  }
}
