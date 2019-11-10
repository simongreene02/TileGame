package com.greatworksinc.tilegame.metadata;

import com.greatworksinc.tilegame.model.GridLocation;

import javax.inject.Inject;
import java.io.IOException;

public class LeverMetadataGenerator implements MetadataGenerator {

    private final MetadataWriter metadataWriter;
    private final DataReader dataReader;

    @Inject
    public LeverMetadataGenerator(MetadataWriter metadataWriter, DataReader dataReader) {
        this.metadataWriter = metadataWriter;
        this.dataReader = dataReader;
    }

    @Override
    public void createMetadata() {
        //TODO: Read maze files into model object.
        //TODO: Randomly generate location.
        //TODO: Repeat generation if location is invalid.
        try {
            System.out.println(dataReader.getNumberOfLevels());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Metadata metadata = new Metadata(GridLocation.of(0, 0));
        metadataWriter.write(metadata);
    }
}
