package com.greatworksinc.tilegame.metadata;

import com.greatworksinc.tilegame.model.GridLocation;

import javax.inject.Inject;

public class LeverMetadataGenerator implements MetadataGenerator {

    private final MetadataWriter metadataWriter;

    @Inject
    public LeverMetadataGenerator(MetadataWriter metadataWriter) {
        this.metadataWriter = metadataWriter;
    }

    @Override
    public void createMetadata() {
        metadataWriter.write(new Metadata(GridLocation.of(0, 0)));
    }
}
