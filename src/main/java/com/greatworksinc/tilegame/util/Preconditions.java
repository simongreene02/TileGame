package com.greatworksinc.tilegame.util;

public class Preconditions {
  private Preconditions() {
    //Stops class for being instanced.
  }

  public static int checkPositiveIntegers(int number) {
    if (number <= 0) {
      throw new IllegalArgumentException("Number value must be a positive integer.");
    }
    return number;
  }
}
