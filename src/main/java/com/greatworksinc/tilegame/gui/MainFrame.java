package com.greatworksinc.tilegame.gui;

import com.google.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;

public class MainFrame extends JFrame {

  private static final Logger log = LoggerFactory.getLogger(MainFrame.class);
  private static final String FRAME_TITLE = "Game";

  @Inject
  public MainFrame(JPanel mainPanel) {
    super(FRAME_TITLE);
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    add(mainPanel);
    //Fonts.configure();
    log.info("init");
  }
}