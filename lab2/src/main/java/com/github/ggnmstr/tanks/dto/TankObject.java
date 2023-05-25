package com.github.ggnmstr.tanks.dto;

import com.github.ggnmstr.tanks.util.Direction;

public record TankObject(int x, int y, int width, int height, Direction rotation) {}