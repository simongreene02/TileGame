package com.greatworksinc.tilegame.metadata;

import javax.inject.Inject;

public class LeverMetadata implements MetadataProcessor {

    private final MetadataWriter metadataWriter;

    @Inject
    public LeverMetadata(MetadataWriter metadataWriter) {
        this.metadataWriter = metadataWriter;
    }

    @Override
    public void createMetadata() {
    }
}
