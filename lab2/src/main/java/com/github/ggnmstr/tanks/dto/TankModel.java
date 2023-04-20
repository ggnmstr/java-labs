package com.github.ggnmstr.tanks.dto;

import com.github.ggnmstr.tanks.util.Direction;

public record TankModel(int x, int y, int width, int height, Direction rotation) {
}
