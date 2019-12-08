package com.greatworksinc.tilegame.metadata;

import com.google.common.io.MoreFiles;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.spi.FileSystemProvider;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class DataReaderTest extends Mockito {

    private Path dir;

    @BeforeEach
    void setUp(TestInfo testInfo) throws IOException {
        dir = generateTestFiles(Integer.parseInt(testInfo.getTags().iterator().next()));
    }

    @AfterEach
    void tearDown() throws IOException {
        MoreFiles.deleteRecursively(dir);
    }

    @Test
    @Tag("0")
    void getNumberOfLevels_zero() throws IOException {
        DataReader dataReader = new DataReader(generateTestFiles(0));
        assertThat(dataReader.getNumberOfLevels()).isEqualTo(0);
    }

    @Test
    @Tag("1")
    void getNumberOfLevels_one() throws IOException {
        DataReader dataReader = new DataReader(generateTestFiles(1));
        assertThat(dataReader.getNumberOfLevels()).isEqualTo(1);
    }

    @Test
    @Tag("10")
    void getNumberOfLevels_ten() throws IOException {
        DataReader dataReader = new DataReader(generateTestFiles(10));
        assertThat(dataReader.getNumberOfLevels()).isEqualTo(10);
    }

    private static Path generateTestFiles(int numberOfFiles) throws IOException {
        Path dir = Files.createTempDirectory("mazeTestFiles");
        for (int i = 0; i < numberOfFiles; i++) {
            Files.createTempFile(dir, "maze", ".csv");
        }
        Files.createTempFile(dir, "maze", ".txt");
        Files.createTempFile(dir, "tiles", ".csv");
        return dir;
    }
}