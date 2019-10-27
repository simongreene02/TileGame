package com.greatworksinc.tilegame.metadata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MetadataWriter {
    public void write(Metadata metadata) {
        try {
            Files.write(Paths.get("metadata.json"), new ObjectMapper().writeValueAsString(metadata).getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
