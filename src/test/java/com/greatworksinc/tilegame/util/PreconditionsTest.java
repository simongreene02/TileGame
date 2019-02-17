package com.greatworksinc.tilegame.util;

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PreconditionsTest {

  @Test
  void checkPositiveIntegers_negative() {
    assertThrows(IllegalArgumentException.class, () -> Preconditions.checkPositiveIntegers(-1));
  }

  @Test
  void checkPositiveIntegers_zero() {
    assertThrows(IllegalArgumentException.class, () -> Preconditions.checkPositiveIntegers(0));
  }

  @Test
  void checkPositiveIntegers_positive() {
    assertThat(Preconditions.checkPositiveIntegers(1)).isEqualTo(1);
  }
}