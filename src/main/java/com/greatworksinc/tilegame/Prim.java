package com.greatworksinc.tilegame;

import com.google.common.annotations.VisibleForTesting;
import com.greatworksinc.tilegame.model.GridLocation;
import com.greatworksinc.tilegame.model.Staircases;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

//Taken from http://jonathanzong.com/blog/2012/11/06/maze-generation-with-prims-algorithm

public class Prim {
  public enum MazeTile {
    FLOOR('-', 15),
    WALL('+', 75),
    START_POS('S', 58),
    END_POS('E', 57);
    private char tile;
    private int gid;
    MazeTile(char tile, int gid) {
      this.tile = tile;
      this.gid = gid;
    }

    public char getTile() {
      return tile;
    }

    public int getGid() {
      return gid;
    }
  }

  private static final String ROWS_ARG = "rows";
  private static final String COLS_ARG = "columns";
  private static final String MAZES_ARG = "mazes";
  private static final String SEED_ARG = "seed";
  private static final String DIRECTORY_ARG = "directory";
  private static final String TILEMODE_ARG = "tileMode";

  private final int numOfRows;
  private final int numOfCols;

  private final Random random;

  private Staircases lastStaircases;


  public static void main(String[] args) throws IOException, ParseException {
    Options options = new Options();

    options.addRequiredOption("r", ROWS_ARG, true, "The number of rows generated in each maze.");
    options.addRequiredOption("c", COLS_ARG, true, "The number of columns generated in each maze.");
    options.addRequiredOption("m", MAZES_ARG, true, "The number of mazes created in maze generation.");
    options.addRequiredOption("s", SEED_ARG, true, "The random seed used in maze generation.");
    options.addRequiredOption("d", DIRECTORY_ARG, true, "The directory that maze files are saved to.");

    options.addOption("t", TILEMODE_ARG, false, "Render outputs as ASCII tiles instead of CSV.");

    CommandLine parsedArgs = new DefaultParser().parse(options, args);

    Prim prim = new Prim(
        Integer.parseInt(parsedArgs.getOptionValue(ROWS_ARG)),
        Integer.parseInt(parsedArgs.getOptionValue(COLS_ARG)),
        Integer.parseInt(parsedArgs.getOptionValue(SEED_ARG)));
    int iterations = Integer.parseInt(parsedArgs.getOptionValue(MAZES_ARG));
    Path path = Paths.get(parsedArgs.getOptionValue(DIRECTORY_ARG));
    Files.createDirectories(path);
    AbstractMazeFileWriter mazeFileWriter = AbstractMazeFileWriter.createMazeFileWriter(
        parsedArgs.hasOption(TILEMODE_ARG), prim, path);
    for (int i = 0; i < iterations; i++) {
      mazeFileWriter.writeFile();
    }
  }

  public Prim(int numOfRows, int numOfCols, long seed) {
    this(numOfRows, numOfCols, new Random(seed));
  }

  @VisibleForTesting Prim(int numOfRows, int numOfCols, Random random) {
    this.numOfRows = numOfRows;
    this.numOfCols = numOfCols;
    this.random = random;
  }

  public MazeTile[][] generateMaze() {
    // dimensions of generated maze

    // build maze and initialize with only walls
    MazeTile[][] maze = new MazeTile[numOfRows][numOfCols];
    for (int x = 0; x < numOfRows; x++) {
      for (int y = 0; y < numOfCols; y++) {
        maze[x][y]=MazeTile.WALL;
      }
    }

    // select random point and open as start node
    Point st = new Point((random.nextInt(numOfRows)), (random.nextInt(numOfCols)), null);
    //maze[st.r][st.c] = MazeTile.START_POS;

    // iterate through direct neighbors of node
    ArrayList<Point> frontier = new ArrayList<>();
    for (int x = -1; x <= 1; x++) {
      for (int y = -1; y <= 1; y++) {
        if (x == 0 && y == 0 || x != 0 && y != 0) {
          continue;
        }
        try {
          if (isPointInList(maze, new Point(st.r + x, st.c + y, null)) && maze[st.r + x][st.c + y] == MazeTile.FLOOR) {
            continue;
          }
        } catch (Exception e) { // ignore ArrayIndexOutOfBounds
          throw new RuntimeException("TODO: Fill in boundry check", e);
        }
        // add eligible points to frontier
        frontier.add(new Point(st.r + x, st.c + y, st));
      }
    }

    Point last = null;
    while (!frontier.isEmpty()) {

      // pick current node at random
      Point cu = frontier.remove(random.nextInt(frontier.size()));
      Point op = cu.opposite();
      try {
        // if both node and its opposite are walls
        if (isPointInList(maze, cu) && maze[cu.r][cu.c] == MazeTile.WALL) {
          if (isPointInList(maze, op) && maze[op.r][op.c] == MazeTile.WALL) {

            // open path between the nodes
            maze[cu.r][cu.c] = MazeTile.FLOOR;
            maze[op.r][op.c] = MazeTile.FLOOR;

            // store last node in order to mark it later
            last = op;

            // iterate through direct neighbors of node, same as earlier
            for (int x = -1; x <= 1; x++) {
              for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0 || x != 0 && y != 0) {
                  continue;
                }
                try {
                  if (isPointInList(maze, new Point(op.r + x, op.c + y, null)) && maze[op.r + x][op.c + y] == MazeTile.FLOOR) {
                    continue;
                  }
                } catch (Exception e) {
                  throw new RuntimeException("TODO: Fill in boundry check", e);
                }
                frontier.add(new Point(op.r + x, op.c + y, op));
              }
            }
          }
        }
      } catch (Exception e) { // ignore NullPointer and ArrayIndexOutOfBounds
        throw new RuntimeException("TODO: Fill in boundry check", e);
      }

      // if algorithm has resolved, clear start and end nodes
      if (frontier.isEmpty()) {
        maze[st.r][st.c] = MazeTile.FLOOR;
        maze[last.r][last.c] = MazeTile.FLOOR;
      }
    }

    lastStaircases = new Staircases(
        GridLocation.of(st.r, st.c),
        GridLocation.of(last.r, last.c)
    );
    return maze;
  }

  @VisibleForTesting
  static boolean isPointInList(MazeTile[][] maze, Point point) {
    return point.r >= 0 && point.r < maze.length && point.c >= 0 && point.c < maze[point.r].length;
  }

  public Staircases getLastStaircases() {
    return lastStaircases;
  }

  static class Point {
    private final Integer r;
    private final Integer c;
    private final Point parent;

    public Point(int x, int y, Point p) {
      r = x;
      c = y;
      parent = p;
    }

    // compute opposite node given that it is in the other direction from the parent
    public Point opposite() {
      if (this.r.compareTo(parent.r) != 0) {
        return new Point(this.r + this.r.compareTo(parent.r), this.c, this);
      }
      if (this.c.compareTo(parent.c) != 0) {
        return new Point(this.r, this.c + this.c.compareTo(parent.c), this);
      }
      return null;
    }
  }
}