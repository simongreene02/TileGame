package com.greatworksinc.tilegame.service;

import com.greatworksinc.tilegame.gui.GamePanel;
import com.greatworksinc.tilegame.model.GridSize;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.swing.text.Position;
import java.awt.*;

import static java.awt.event.KeyEvent.*;

public class MovementService {

  private static final Logger log = LoggerFactory.getLogger(MovementService.class);
  private final GridSize gridSize;

  @Inject
  public MovementService(GridSize gridSize) {
    this.gridSize = gridSize;
  }

  public boolean move(Point position, int keyCode) {//, GridSize gridSize) {
    //GridSize gridSize = new GridSize(10, 10);
    switch (keyCode) {
      case VK_UP:
        if (position.y > 0) {
          position.y--;
          return true;
        } else {
          return false;
        }
      case VK_DOWN:
        if (position.y < gridSize.getNumOfRows() - 1) {
          position.y++;
          return true;
        } else {
          return false;
        }
      case VK_LEFT:
        if (position.x > 0) {
          position.x--;
          return true;
        } else {
          return false;
        }
      case VK_RIGHT:
        if (position.x < gridSize.getNumOfCols() - 1) {
          position.x++;
          return true;
        } else {
          return false;
        }
      default:
        log.info("{} keycode was pressed", keyCode);
        return false;
    }
  }
}
