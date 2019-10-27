package com.greatworksinc.tilegame.metadata;

import com.google.inject.PrivateModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MetadataModule extends PrivateModule {
    private static final Logger log = LoggerFactory.getLogger(MetadataModule.class);
    @Override
    protected void configure() {
        bind(MetadataGenerator.class).to(LeverMetadataGenerator.class).in(Singleton.class);
        expose(MetadataGenerator.class);
    }

    @Provides
    @Singleton
    private MetadataWriter provideMetadataWriter() {
        log.info("provideBorderLayout");
        return new MetadataWriter();
    }

    @Provides
    @Singleton
    private Path provideMazePath() {
        log.info("provideMazePath");
        Path mazePath = Paths.get("/home/ninja/ws/github/TileGame/src/main/resources"); //TODO: Read from command line.
        if (Files.isDirectory(mazePath)) {
            return mazePath;
        }
        throw new IllegalArgumentException("Path must be a directory.");
    }
}
