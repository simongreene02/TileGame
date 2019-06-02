package com.greatworksinc.tilegame;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class AbstractMazeFileWriter {
  public void writeFile() {
    try {
      Files.write(destinationFile(), mazeOutput().getBytes());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  abstract protected Path destinationFile();
  abstract protected String mazeOutput();

  public static class MazeWriterCSV extends AbstractMazeFileWriter {

    private int counter = 0;
    private Prim mazeGenerator;
    private Path path;

    public MazeWriterCSV(Prim mazeGenerator, Path path) {
      this.mazeGenerator = mazeGenerator;
      this.path = path;
    }

    @Override
    protected Path destinationFile() {
      counter++;
      return Paths.get(path.toString(), "mazeGenerator" + counter + ".txt");
    }

    @Override
    protected String mazeOutput() {
      StringBuilder out = new StringBuilder();
      Prim.MazeTile[][] maze = mazeGenerator.generateMaze();
      for (int x = 0; x < maze.length; x++) {
        for (int y = 0; y < maze[x].length; y++) {
          out.append(maze[x][y].getGid()+",");
        }
        out.append('\n');
      }
      out.delete(out.length()-2, out.length());
      return out.toString();
    }
  }

}
