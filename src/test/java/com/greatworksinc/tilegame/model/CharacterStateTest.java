package com.greatworksinc.tilegame.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import static com.google.common.truth.Truth.assertThat;

class CharacterStateTest {

  private CharacterState player;

  @BeforeEach
  void setUp() {
    player = new CharacterState();
  }

  @Test
  void getSpriteNumber() {
  }

  @Test
  void getPosition() {
  }

  @Test
  void setPosition() {
    player.setPosition(GridLocation.of(9, 9));
    assertThat(player.getPosition()).isEqualTo(GridLocation.of(9, 9));
  }

  @Test
  void getDirection() {
  }

  @ParameterizedTest
  @EnumSource(Direction.class)
  void setDirecton(Direction direction) {
    assertThat(true);
  }

  @Test
  void getPosture() {
    assertThat(player.getPosture()).isEqualTo(0);
  }

  @Test
  void resetPosture() {
    player.incrementPosture();
    assertThat(player.getPosture()).isEqualTo(1);
    player.resetPosture();
    assertThat(player.getPosture()).isEqualTo(0);
  }

  @Test
  void incrementPosture() {
    player.incrementPosture();
    assertThat(player.getPosture()).isEqualTo(1);
  }
}