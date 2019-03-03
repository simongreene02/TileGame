package com.greatworksinc.tilegame.tools;

import com.greatworksinc.tilegame.model.GridDataSource;

public class ForegroundGenerator implements GridDataSource {

  @Override
  public String getDataAsString() {
    return "58,0,0,0,0,0,0,0,0,0,0,\n" +
        "0,0,0,0,0,0,0,0,0,0,0,\n" +
        "0,0,0,0,0,0,0,0,0,0,0,\n" +
        "0,0,0,0,0,0,0,0,0,0,0,\n" +
        "0,0,0,0,0,0,0,0,0,0,0,\n" +
        "0,0,0,0,0,0,0,0,0,0,0,\n" +
        "0,0,0,0,0,0,0,0,0,0,0,\n" +
        "0,0,0,0,0,0,0,0,0,0,0,\n" +
        "0,0,0,0,0,0,0,0,0,0,0,\n" +
        "0,0,0,0,0,0,0,0,0,0,0,\n" +
        "0,0,0,0,0,0,0,0,0,0,57";
  }
}
