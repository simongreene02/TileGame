package com.greatworksinc.tilegame.metadata;

import com.greatworksinc.tilegame.model.GridLocation;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;

public class DataReader {

    private static final Predicate<Path> IS_MAZE_FILE = path -> path.toString().toLowerCase().endsWith(".csv") &&
            path.getFileName().toString().toLowerCase().startsWith("maze");
    private final Path mazeDirectoryPath;

    @Inject
    public DataReader(Path mazeDirectoryPath) {
        this.mazeDirectoryPath = mazeDirectoryPath;
    }

    public int getNumberOfLevels() throws IOException {
        return (int) Files.list(mazeDirectoryPath).filter(IS_MAZE_FILE).count();
    }
}
