package com.greatworksinc.tilegame;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.greatworksinc.tilegame.model.GridLocation;
import com.greatworksinc.tilegame.model.MazeTile;
import com.greatworksinc.tilegame.model.Staircases;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

//Taken from http://jonathanzong.com/blog/2012/11/06/maze-generation-with-prims-algorithm

public class Prim {
  private static final String ROWS_MIN_ARG = "minRows";
  private static final String ROWS_MAX_ARG = "maxRows";
  private static final String COLS_MIN_ARG = "minColumns";
  private static final String COLS_MAX_ARG = "maxColumns";
  private static final String MAZES_ARG = "mazes";
  private static final String SEED_ARG = "seed";
  private static final String DIRECTORY_ARG = "directory";
  private static final String TILEMODE_ARG = "tileMode";

  private final int minRows;
  private final int maxRows;
  private final int minCols;
  private final int maxCols;

  private final Random random;

  private Staircases lastStaircases;


  public static void main(String[] args) throws IOException, ParseException {
    Options options = new Options();

    options.addRequiredOption("r_min", ROWS_MIN_ARG, true, "The minimum number of rows generated in each maze.");
    options.addRequiredOption("r_max", ROWS_MAX_ARG, true, "The maximum number of rows generated in each maze.");
    options.addRequiredOption("c_min", COLS_MIN_ARG, true, "The minimum number of columns generated in each maze.");
    options.addRequiredOption("c_max", COLS_MAX_ARG, true, "The maximum number of columns generated in each maze.");
    options.addRequiredOption("m", MAZES_ARG, true, "The number of mazes created in maze generation.");
    options.addRequiredOption("s", SEED_ARG, true, "The random seed used in maze generation.");
    options.addRequiredOption("d", DIRECTORY_ARG, true, "The directory that maze files are saved to.");

    options.addOption("t", TILEMODE_ARG, false, "Render outputs as ASCII tiles instead of CSV.");

    CommandLine parsedArgs = new DefaultParser().parse(options, args);

    Prim prim = new Prim(
        Integer.parseInt(parsedArgs.getOptionValue(ROWS_MIN_ARG)),
        Integer.parseInt(parsedArgs.getOptionValue(ROWS_MAX_ARG)),
        Integer.parseInt(parsedArgs.getOptionValue(COLS_MIN_ARG)),
        Integer.parseInt(parsedArgs.getOptionValue(COLS_MAX_ARG)),
        Long.parseLong(parsedArgs.getOptionValue(SEED_ARG)));
    int iterations = Integer.parseInt(parsedArgs.getOptionValue(MAZES_ARG));
    Path path = Paths.get(parsedArgs.getOptionValue(DIRECTORY_ARG));
    Files.createDirectories(path);
    AbstractMazeFileWriter mazeFileWriter = AbstractMazeFileWriter.createMazeFileWriter(
        parsedArgs.hasOption(TILEMODE_ARG), prim, path);
    for (int i = 0; i < iterations; i++) {
      mazeFileWriter.writeFile();
    }
  }


  public Prim(int minRows, int maxRows, int minCols, int maxCols, long seed) {
    this(minRows, maxRows, minCols, maxCols, new Random(seed));
  }

  @VisibleForTesting
  Prim(int minRows, int maxRows, int minCols, int maxCols, Random random) {
    this.minRows = minRows;
    this.maxRows = maxRows;
    this.minCols = minCols;
    this.maxCols = maxCols;
    this.random = random;
  }

  public MazeTile[][] generateMaze() {
    // dimensions of generated maze
    int numOfRows;
    if (minRows > maxRows) {
      throw new IllegalArgumentException("The minimum number of rows must be equal to or less than the maximum.");
    } else if (minRows == maxRows) {
      numOfRows = minRows;
    } else {
      numOfRows = random.nextInt(maxRows - minRows) + minRows;
    }
    int numOfCols;
    if (minCols > maxCols) {
      throw new IllegalArgumentException("The minimum number of cols must be equal to or less than the maximum.");
    } else if (minCols == maxCols) {
      numOfCols = minCols;
    } else {
      numOfCols = random.nextInt(maxCols - minCols) + minCols;
    }
    // build maze and initialize with only walls
    MazeTile[][] maze = new MazeTile[numOfRows][numOfCols];
    for (int x = 0; x < numOfRows; x++) {
      for (int y = 0; y < numOfCols; y++) {
        maze[x][y] = MazeTile.WALL;
      }
    }

    // select random point and open as start node
    Point startingPoint = new Point((random.nextInt(numOfRows)), (random.nextInt(numOfCols)));
    maze[startingPoint.row][startingPoint.col] = MazeTile.FLOOR;

    // iterate through direct neighbors of node
    List<Point> frontier = new ArrayList<>(getAdjacentPoints(maze, startingPoint));

    Point last = null;
    while (!frontier.isEmpty()) {

      // pick current node at random
      Point cu = frontier.remove(random.nextInt(frontier.size()));
      Point oppositePoint = cu.opposite();
        // if both node and its opposite are walls
      if (isPointInMaze(maze, cu) && maze[cu.row][cu.col] != MazeTile.FLOOR) {
        if (isPointInMaze(maze, oppositePoint) && maze[oppositePoint.row][oppositePoint.col] != MazeTile.FLOOR) {
          // open path between the nodes
          maze[cu.row][cu.col] = MazeTile.FLOOR;
          maze[oppositePoint.row][oppositePoint.col] = MazeTile.FLOOR;

          // store last node in order to mark it later
          last = oppositePoint;

          // iterate through direct neighbors of node, same as earlier
          frontier.addAll(getAdjacentPoints(maze, oppositePoint));
        }
      }

      // if algorithm has resolved, clear start and end nodes
      if (frontier.isEmpty()) {
        maze[startingPoint.row][startingPoint.col] = MazeTile.FLOOR;
        maze[last.row][last.col] = MazeTile.FLOOR;
      }
    }

    lastStaircases = new Staircases(
        GridLocation.of(startingPoint.row, startingPoint.col),
        GridLocation.of(last.row, last.col)
    );
    return maze;
  }

  @VisibleForTesting
  static boolean isPointInMaze(MazeTile[][] maze, Point point) {
    return point.row >= 0 && point.row < maze.length && point.col >= 0 && point.col < maze[point.row].length;
  }

  public Staircases getLastStaircases() {
    return lastStaircases;
  }

  private static ImmutableList<Point> getNews(Point parentPoint) {
    return ImmutableList.of(
        new Point(parentPoint.row - 1, parentPoint.col, parentPoint),
        new Point(parentPoint.row, parentPoint.col - 1, parentPoint),
        new Point(parentPoint.row + 1, parentPoint.col, parentPoint),
        new Point(parentPoint.row, parentPoint.col + 1, parentPoint));
  }

  @VisibleForTesting
  static ImmutableList<Point> getAdjacentPoints(MazeTile[][] maze, Point parentPoint) {
//    ArrayList<Point> output = new ArrayList<>();
//    for (Point adjacentPoint : getNews(parentPoint)) {
//      if (isPointInMaze(maze, adjacentPoint) && maze[adjacentPoint.row][adjacentPoint.col] == MazeTile.WALL) {
//        // add eligible points to frontier
//        output.add(adjacentPoint);
//      }
//    }

    return getNews(parentPoint).stream()
        .filter(adjacentPoint -> isPointInMaze(maze, adjacentPoint) && maze[adjacentPoint.row][adjacentPoint.col] == MazeTile.WALL)
        .collect(ImmutableList.toImmutableList());
  }

  static class Point {
    private final int row;
    private final int col;
    private final @Nullable
    Point parent;

    public Point(int row, int col, Point parent) {
      this.row = row;
      this.col = col;
      this.parent = parent;
    }

    public Point(int row, int col) {
      this(row, col, null);
    }

    /**
     * compute opposite node given that it is in the other direction from the parent
     *
     * @return
     */
    public @Nullable
    Point opposite() {
      if (parent == null) {
        throw new IllegalStateException("Parent is null.");
      }
      if (row == parent.row && col == parent.col) {
        return null;
      }
      int newRow = row;
      int newCol = col;
      if (row != parent.row) {
        if (row < parent.row) {
          newRow--;
        } else {
          newRow++;
        }
      }
      if (col != parent.col) {
        if (col < parent.col) {
          newCol--;
        } else {
          newCol++;
        }
      }
      return new Point(newRow, newCol, this);
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      Point point = (Point) o;
      return row == point.row &&
          col == point.col;
    }

    @Override
    public int hashCode() {
      return Objects.hash(row, col);
    }
  }
}