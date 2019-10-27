package com.greatworksinc.tilegame.metadata;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new MetadataModule());
        MetadataGenerator metadataProcessor = injector.getInstance(MetadataGenerator.class);
        metadataProcessor.createMetadata();
        log.info("Created injector.");
    }
}
