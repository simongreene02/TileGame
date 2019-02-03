package com.greatworksinc.tilegame.service;

import com.google.common.collect.ImmutableSet;
import com.greatworksinc.tilegame.model.CharacterState;
import com.greatworksinc.tilegame.model.GridLayer;
import com.greatworksinc.tilegame.util.MoreResources;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MovementServiceTest {

  private static final ImmutableSet<Integer> INACCESSIBLE_SPRITES = ImmutableSet.of(75);

  private CharacterState player;
  private MovementService movementService;

  @BeforeEach
  void setUp() {
    player = new CharacterState();
    movementService = new MovementService(INACCESSIBLE_SPRITES,
        new GridLayer(MoreResources.getResource("Maze_Layer_test.csv")));
  }

  @Test
  void move() {
  }
}