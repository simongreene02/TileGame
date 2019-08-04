package com.greatworksinc.tilegame;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.greatworksinc.tilegame.model.MazeTile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class AbstractMazeFileWriter {

  private int counter;
  protected Prim mazeGenerator;
  private Path path;

  public AbstractMazeFileWriter(Prim mazeGenerator, Path path) {
    this.mazeGenerator = mazeGenerator;
    this.path = path;
  }

  public void writeFile() {
    try {
      Files.write(destinationFile(), mazeOutput().getBytes());
      Files.write(staircaseFile(), new ObjectMapper().writeValueAsString(mazeGenerator.getLastStaircases()).getBytes());
      counter++;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private Path destinationFile() {
    return Paths.get(path.toString(), "maze" + counter + ".csv");
  }

  private Path staircaseFile() {
    return Paths.get(path.toString(), "maze" + counter + "_stairs.json");
  }

  public static AbstractMazeFileWriter createMazeFileWriter(boolean isASCII, Prim mazeGenerator, Path path) {
    if (isASCII) {
      return new MazeWriterASCII(mazeGenerator, path);
    } else {
      return new MazeWriterCSV(mazeGenerator, path);
    }
  }

  abstract protected String mazeOutput();

  private static class MazeWriterCSV extends AbstractMazeFileWriter {

    public MazeWriterCSV(Prim mazeGenerator, Path path) {
      super(mazeGenerator, path);
    }

    @Override
    protected String mazeOutput() {
      StringBuilder out = new StringBuilder();
      MazeTile[][] maze = mazeGenerator.generateMaze();
      for (int x = 0; x < maze.length; x++) {
        for (int y = 0; y < maze[x].length; y++) {
          out.append(maze[x][y].getGid()+",");
        }
        out.append('\n');
      }
      out.delete(out.length()-2, out.length()); //Removes comma and newline from end of file to avoid confusing interpreters.
      return out.toString();
    }
  }

  private static class MazeWriterASCII extends AbstractMazeFileWriter {

    public MazeWriterASCII(Prim mazeGenerator, Path path) {
      super(mazeGenerator, path);
    }

    @Override
    protected String mazeOutput() {
      StringBuilder out = new StringBuilder();
      MazeTile[][] maze = mazeGenerator.generateMaze();
      for (int x = 0; x < maze.length; x++) {
        for (int y = 0; y < maze[x].length; y++) {
          out.append(maze[x][y].getTile());
        }
        out.append('\n');
      }
      out.delete(out.length()-1, out.length());
      return out.toString();
    }
  }
}
