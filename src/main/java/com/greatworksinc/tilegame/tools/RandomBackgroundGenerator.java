package com.greatworksinc.tilegame.tools;

import com.greatworksinc.tilegame.model.GridDataSource;
import com.greatworksinc.tilegame.model.GridSize;

import javax.inject.Inject;
import java.awt.*;
import java.util.Random;

public class RandomBackgroundGenerator implements GridDataSource {

  private final GridSize gridSize;
  private final Random random;

  @Inject
  public RandomBackgroundGenerator(GridSize gridSize, Random random) {
    this.gridSize = gridSize;
    this.random = random;
  }

  @Override
  public String getDataAsString() {
    return generateMaze(gridSize.getNumOfCols(), gridSize.getNumOfRows());
  }

  private String generateMaze(int width, int height) {
    boolean[][] maze = new boolean[height][width];
    Point cursor = new Point();
    int direction;
    maze[random.nextInt(height / 2) * 2][random.nextInt(width / 2) * 2] = true;


    do {
      do {
        cursor.setLocation(random.nextInt(width / 2) * 2, random.nextInt(height / 2) * 2);
        direction = random.nextInt(4) * 90;
      } while (isTileInDirection(cursor, direction, maze) || !maze[cursor.y][cursor.x]);
      boolean isForward = !isTileInDirection(cursor, direction, maze);
      boolean isLeft = !isTileInDirection(cursor, direction - 90, maze);
      boolean isRight = !isTileInDirection(cursor, direction + 90, maze);
      while (isForward || isLeft || isRight) {
        if (!isForward || random.nextInt(2) == 0) {
          if (isLeft && isRight) {
            if (random.nextBoolean()) {
              direction -= 90;
            } else {
              direction += 90;
            }
          } else if (isLeft) {
            direction -= 90;
          } else if (isRight) {
            direction += 90;
          }
          direction = direction % 360;
        }
        int x = cursor.x + (int) Math.cos(Math.toRadians(direction));
        int y = cursor.y + (int) Math.sin(Math.toRadians(direction));
        maze[y][x] = true;
        x = cursor.x + (int) Math.cos(Math.toRadians(direction)) * 2;
        y = cursor.y + (int) Math.sin(Math.toRadians(direction)) * 2;
        maze[y][x] = true;
        cursor.setLocation(x, y);
        isForward = !isTileInDirection(cursor, direction, maze);
        isLeft = !isTileInDirection(cursor, direction - 90, maze);
        isRight = !isTileInDirection(cursor, direction + 90, maze);
      }
    } while (!isMazeFinished(maze));

    StringBuilder outputString = new StringBuilder();
//    for (boolean[] row : maze) {
//      for (boolean tile : row) {
//        outputString.append(tile ? "15," : "75,");
//      }
//      outputString.append("\n");
//    }

    for (int i = 0; i < maze.length; i++) {
      for (int j = 0; j < maze[i].length; j++) {
        outputString.append(maze[i][j] ? "15" : "75");
        if (i != maze.length-1 || j != maze[i].length-1) {
          outputString.append(",");
        }
      }
      if (i != maze.length-1) {
        outputString.append("\n");
      }
    }

    System.out.println(outputString.toString());
    return outputString.toString();
  }

  private static boolean isTileInDirection(Point cursor, int direction, boolean[][] maze) {
    int x = cursor.x + (int) Math.cos(Math.toRadians(direction)) * 2;
    int y = cursor.y + (int) Math.sin(Math.toRadians(direction)) * 2;
    if (y >= 0 && y < maze.length && x >= 0 && x < maze[0].length) {
      return maze[y][x];
    } else {
      return true;
    }
  }

  private static boolean isMazeFinished(boolean[][] maze) {
    for (int i = 0; i <= maze.length; i += 2) {
      for (int j = 0; j <= maze[0].length; j += 2) {
        if (!maze[i][j]) {
          return false;
        }
      }
    }
    return true;
  }
}
