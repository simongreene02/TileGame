package com.greatworksinc.tilegame;

import com.google.common.annotations.VisibleForTesting;

import java.util.ArrayList;
import java.util.Random;

//Taken from http://jonathanzong.com/blog/2012/11/06/maze-generation-with-prims-algorithm

public class Prim {
  private final int numOfRows;
  private final int numOfCols;

  private final Random random;


  public static void main(String[] args) {
    if (args.length != 4) {
      System.out.println("Usage: ./bin/tilegame <number of rows> <number of columns> <number of mazes> <seed>");
      System.out.println("Example: ./bin/tilegame 18 10 3 0");
      return;
    }


    Prim prim = new Prim(Integer.parseInt(args[0]), Integer.parseInt(args[1]), Integer.parseInt(args[3]));
    int iterations = Integer.parseInt(args[2]);
    for (int i = 0; i < iterations; i++) {
      prim.generateMaze();
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

  public void generateMaze() {
    // dimensions of generated maze

    // build maze and initialize with only walls
    StringBuilder s = new StringBuilder(numOfCols);
    for (int x = 0; x < numOfCols; x++) {
      s.append('+');
    }
    char[][] maz = new char[numOfRows][numOfCols];
    for (int x = 0; x < numOfRows; x++) {
      maz[x] = s.toString().toCharArray();
    }

    // select random point and open as start node
    Point st = new Point((random.nextInt(numOfRows)), (random.nextInt(numOfCols)), null);
    maz[st.r][st.c] = 'S';

    // iterate through direct neighbors of node
    ArrayList<Point> frontier = new ArrayList<>();
    for (int x = -1; x <= 1; x++) {
      for (int y = -1; y <= 1; y++) {
        if (x == 0 && y == 0 || x != 0 && y != 0) {
          continue;
        }
        try {
          if (isPointInList(maz, new Point(st.r + x, st.c + y, null)) && maz[st.r + x][st.c + y] == '-') {
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
        if (isPointInList(maz, cu) && maz[cu.r][cu.c] == '+') {
          if (isPointInList(maz, op) && maz[op.r][op.c] == '+') {

            // open path between the nodes
            maz[cu.r][cu.c] = '-';
            maz[op.r][op.c] = '-';

            // store last node in order to mark it later
            last = op;

            // iterate through direct neighbors of node, same as earlier
            for (int x = -1; x <= 1; x++) {
              for (int y = -1; y <= 1; y++) {
                if (x == 0 && y == 0 || x != 0 && y != 0) {
                  continue;
                }
                try {
                  if (isPointInList(maz, new Point(op.r + x, op.c + y, null)) && maz[op.r + x][op.c + y] == '-') {
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
        maz[last.r][last.c] = 'E';
      }
    }

    // print final maze
    for (int i = 0; i < numOfRows; i++) {
      for (int j = 0; j < numOfCols; j++) {
        System.out.print(maz[i][j]);
      }
      System.out.println();
    }
  }

  @VisibleForTesting
  static boolean isPointInList(char[][] maze, Point point) {
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