package com.greatworksinc.tilegame.gui;

import javax.swing.*;
import java.awt.*;

public abstract class Abstract2DPanel extends JPanel {

  @Override
  protected final void paintComponent(Graphics g) {
    super.paintComponent(g);
    paintComponent((Graphics2D)g);
    requestFocusInWindow(); // NOTE: This enables KeyListener on JPanel. This has to be called after JFrame is set to visible
  }

  abstract protected void paintComponent(Graphics2D g);

}
