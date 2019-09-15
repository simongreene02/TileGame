package com.greatworksinc.tilegame.tools;

import com.google.common.collect.ImmutableMap;
import com.greatworksinc.tilegame.model.GridLocation;
import com.greatworksinc.tilegame.util.MoreResources;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.google.common.truth.Truth.assertThat;

class BackgroundMazeReaderTest {
//  @Test
//  void readFile() throws URISyntaxException {
//    Path path = Paths.get(MoreResources.getResource("mazeReadingTest.csv").toURI());
//    ImmutableMap<GridLocation, Integer> maze = BackgroundMazeReader.readFile(path);
//    assertThat(maze).containsExactlyEntriesIn(ImmutableMap.builder()
//        .put(GridLocation.of(0, 0), 15)
//        .put(GridLocation.of(0, 1), 15)
//        .put(GridLocation.of(0, 2), 15)
//        .put(GridLocation.of(0, 3), 75)
//        .put(GridLocation.of(1, 0), 15)
//        .put(GridLocation.of(1, 1), 75)
//        .put(GridLocation.of(1, 2), 15)
//        .put(GridLocation.of(1, 3), 75)
//        .put(GridLocation.of(2, 0), 15)
//        .put(GridLocation.of(2, 1), 75)
//        .put(GridLocation.of(2, 2), 15)
//        .put(GridLocation.of(2, 3), 15)
//        .build());
//  }
}