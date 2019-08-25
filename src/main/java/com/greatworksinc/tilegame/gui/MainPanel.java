package com.greatworksinc.tilegame.gui;

import com.google.inject.Inject;
import com.greatworksinc.tilegame.model.GridSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

  private static final Logger log = LoggerFactory.getLogger(MainPanel.class);
  private GamePanel panel;

  @Inject
  public MainPanel(LayoutManager layout, GamePanel panel) {
    this.panel = panel;
    setLayout(layout);
    add(panel);
    log.info("init");
  }

  @Override
  public Dimension getPreferredSize() {
    GridSize gridSize = panel.getGridSize();
    return new Dimension(gridSize.getNumOfCols()*32, gridSize.getNumOfRows()*32);
  }
}
