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
    assertThat(player.getSpriteNumber()).isEqualTo(73);
  }

  @Test
  void getPosition() {
    assertThat(player.getPosition()).isEqualTo(GridLocation.of(0, 0));
  }

  @Test
  void setPosition() {
    player.setPosition(GridLocation.of(9, 9));
    assertThat(player.getPosition()).isEqualTo(GridLocation.of(9, 9));
  }

  @Test
  void getDirectionDefault() {
    assertThat(player.getDirection()).isSameAs(Direction.EAST);
  }

  @ParameterizedTest
  @EnumSource(Direction.class)
  void setDirecton(Direction direction) {
    player.setDirection(direction);
    assertThat(player.getDirection()).isSameAs(direction);
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