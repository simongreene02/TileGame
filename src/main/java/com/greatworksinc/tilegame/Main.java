package com.greatworksinc.tilegame;

import com.google.inject.Guice;
import com.google.inject.Injector;

import javax.swing.*;

public class Main {
  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> {
      Injector injector = Guice.createInjector(new TileGameModule());
      JFrame jFrame = injector.getInstance(JFrame.class);
      jFrame.pack();
      jFrame.setVisible(true);
    });
  }
}
