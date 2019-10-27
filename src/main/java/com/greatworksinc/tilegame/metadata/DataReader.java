package com.greatworksinc.tilegame.metadata;

import com.greatworksinc.tilegame.model.GridLocation;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Predicate;

public class DataReader {

    private static final Predicate<Path> IS_MAZE_FILE = path -> path.endsWith(".csv"); //TODO: Starts with "maze"
    private final Path mazeDirectoryPath;

    @Inject
    public DataReader(Path mazeDirectoryPath) {
        this.mazeDirectoryPath = mazeDirectoryPath;
    }

    public int getNumberOfLevels() throws IOException {
        return (int) Files.list(mazeDirectoryPath).filter(IS_MAZE_FILE).count();
    }

    public Metadata getDataForLevel(int level) {
        //TODO: Read maze files into model object.
        //TODO: Randomly generate location.
        //TODO: Repeat generation if location is invalid.
        return new Metadata(GridLocation.of(0, 0));
    }
}
