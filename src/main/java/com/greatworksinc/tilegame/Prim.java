package com.greatworksinc.tilegame;

import com.google.common.annotations.VisibleForTesting;
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
  enum MazeTile {
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

  private final int numOfRows;
  private final int numOfCols;

  private final Random random;


  public static void main(String[] args) throws IOException {
    Options options = new Options();

    options.addRequiredOption("r", "rows", true, "The number of rows generated in each maze.");
    options.addRequiredOption("c", "columns", true, "The number of columns generated in each maze.");
    options.addRequiredOption("m", "mazes", true, "The number of mazes created in maze generation.");
    options.addRequiredOption("s", "seed", true, "The random seed used in maze generation.");
    options.addRequiredOption("d", "directory", true, "The directory that maze files are saved to.");

    options.addOption("t", "tileMode", false, "Render outputs as ASCII tiles instead of CSV.");
    options.addOption("p", "printToConsole", false, "Print outputs to console instead of writing them to file.");

    CommandLine parsedArgs = null;
    try {
      parsedArgs = new DefaultParser().parse( options, args);
    } catch (ParseException e) {
      e.printStackTrace();
    }

    Prim prim = new Prim(Integer.parseInt(parsedArgs.getOptionValue("rows")), Integer.parseInt(parsedArgs.getOptionValue("columns")),
        Integer.parseInt(parsedArgs.getOptionValue("seed")));
    int iterations = Integer.parseInt(parsedArgs.getOptionValue("mazes"));
    Path path = Paths.get(parsedArgs.getOptionValue("directory"));
    Files.createDirectories(path);
    AbstractMazeFileWriter mazeFileWriter = AbstractMazeFileWriter.createMazeFileWriter(parsedArgs.hasOption("tileMode"), prim, path);
    for (int i = 0; i < iterations; i++) {
//      Prim.generateOutputInCSV(prim.generateMaze(), Paths.get(path.toString(), "maze" + i + ".txt"));
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
    maze[st.r][st.c] = MazeTile.START_POS;

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

      // if algorithm has resolved, mark end node
      if (frontier.isEmpty()) {
        maze[last.r][last.c] = MazeTile.END_POS;
      }
    }
    return maze;
  }

  private static void generateOutputInCSV(MazeTile[][] maze, Path destinationFile) {
    StringBuilder out = new StringBuilder();
    for (int x = 0; x < maze.length; x++) {
      for (int y = 0; y < maze[x].length; y++) {
        out.append(maze[x][y].getGid()+",");
      }
      out.append('\n');
    }
    out.delete(out.length()-2, out.length());
    writeFile(out.toString(), destinationFile);
  }

  private static void generateOutputInASCII(MazeTile[][] maze, Path destinationFile) {
    StringBuilder out = new StringBuilder();
    for (int x = 0; x < maze.length; x++) {
      for (int y = 0; y < maze[x].length; y++) {
        out.append(maze[x][y].getTile());
      }
      out.append('\n');
    }
    out.delete(out.length()-1, out.length());
    writeFile(out.toString(), destinationFile);
  }

  private static void writeFile(String mazeOutput, Path destinationFile) {
    try {
      Files.write(destinationFile, mazeOutput.getBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @VisibleForTesting
  static boolean isPointInList(MazeTile[][] maze, Point point) {
    return point.r >= 0 && point.r < maze.length && point.c >= 0 && point.c < maze[point.r].length;
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