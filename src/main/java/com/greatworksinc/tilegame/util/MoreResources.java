package com.greatworksinc.tilegame.util;

import com.google.common.io.Resources;
import com.google.inject.Provides;

import java.net.URL;

public class MoreResources {
  public static URL getResource(String resourceName) {
    return Resources.getResource(resourceName);
  }
}
