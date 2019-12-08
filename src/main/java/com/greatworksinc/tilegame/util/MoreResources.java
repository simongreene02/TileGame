package com.greatworksinc.tilegame.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystemAlreadyExistsException;
import java.nio.file.FileSystems;
import java.util.Collections;

import com.google.common.io.Resources;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A simple wrapper for {@link Resources} with support for enabling resource access to jar.
 * If given resource name is located in a jar, it registers the jar uri as a file system to prevent:
 * <pre>
 *    Caused by: java.nio.file.FileSystemNotFoundException
 *           at com.sun.nio.zipfs.ZipFileSystemProvider.getFileSystem(ZipFileSystemProvider.java:171)
 *           at com.sun.nio.zipfs.ZipFileSystemProvider.getPath(ZipFileSystemProvider.java:157)
 *           at java.nio.file.Paths.get(Paths.java:143)
 * </pre>
 * <p>
 * Note: attempt to create already existing file system will gives warning.
 */
public class MoreResources {

  private static final Logger log = LoggerFactory.getLogger(MoreResources.class);
  private static final String SCHEME_REQUIRES_EXPLICIT_FS = "jar";

  // no instance
  private MoreResources() {}

  /**
   * Creates {@link URL} that represents the given resourceName. If the resource is in jar file, the
   * method automatically create file system.
   *
   * @param resourceName to get
   * @return {@link URL} for the given resourceName
   * @throws IllegalArgumentException if the resource is not found
   * @throws IllegalStateException    if new file system creation fails
   */
  public static URL getResource(String resourceName) {
    URL url = Resources.getResource(resourceName);
    try {
      URI uri = url.toURI();
      if (SCHEME_REQUIRES_EXPLICIT_FS.equals(uri.getScheme())) {
        try {
          FileSystems.newFileSystem(uri, Collections.emptyMap());
        } catch (FileSystemAlreadyExistsException e) {
          log.debug("File system already exists for URI={}", uri);
        }
      }
    } catch (URISyntaxException e) {
      throw new IllegalArgumentException(String.format("resourceName=%s; URL=%s not found.",
              resourceName, url));
    } catch (IOException e) {
      throw new IllegalStateException(String.format("Failed to create file system for %s", url));
    }
    return url;
  }
}