package com.greatworksinc.tilegame.tools;

import com.google.common.collect.ImmutableMap;
import com.greatworksinc.tilegame.model.GridDataSource;
import com.greatworksinc.tilegame.model.GridDataSourceGenerator;
import com.greatworksinc.tilegame.model.GridLocation;
import com.greatworksinc.tilegame.model.GridSize;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;
import java.util.Random;

@Singleton
public class RandomBackgroundGenerator implements GridDataSourceGenerator {

  private final GridSize gridSize;
  private final Random random;

  /**
   * Generated level used currently. Is not final and can be mutated by the function generateNewMap
   */
  private ImmutableMap<GridLocation, Integer> generatedMap;

  @Inject
  public RandomBackgroundGenerator(GridSize gridSize, Random random) {
    this.gridSize = gridSize;
    this.random = random;

    generateNewMap();
  }

  @Override
  public ImmutableMap<GridLocation, Integer> getDataAsMap() {
    return generatedMap;
  }

  @Override
  public void generateNewMap() {
    int width = gridSize.getNumOfCols();
    int height = gridSize.getNumOfRows();
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

    ImmutableMap.Builder<GridLocation, Integer> outList = ImmutableMap.builder();

    for (int i = 0; i < gridSize.getNumOfCols(); i++) {
      for (int j = 0; j < gridSize.getNumOfRows(); j++) {
        if (maze[j][i]) {
          outList.put(GridLocation.of(i, j), 15);
        } else {
          outList.put(GridLocation.of(i, j), 75);
        }
      }
    }

    generatedMap = outList.build();
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
