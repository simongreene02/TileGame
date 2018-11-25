package com.greatworksinc.tilegame.service;

import com.greatworksinc.tilegame.gui.GamePanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

import static java.awt.event.KeyEvent.*;

public class MovementService {
  private static final Logger log = LoggerFactory.getLogger(MovementService.class);
  public boolean move(Point position, int keyCode) {
    switch (keyCode) {
      case VK_UP:
        break;
      case VK_DOWN:
        break;
      case VK_LEFT:
        break;
      case VK_RIGHT:
        break;
      default:
        log.info("{} keycode was pressed", keyCode);
    }
    throw new UnsupportedOperationException("TODO");
  }
}
