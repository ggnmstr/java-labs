package com.github.ggnmstr.tanks.dto;

import java.util.List;

//record Block(Position position, BlockType blockType) {
//    enum BlockType {
//        BUSH,
//        WALL,
//
//    }
//}

public record GameObjects(TankModel mainPlayer, Position base, List<Position> blocks,
                          List<TankModel> enemies, List<Position> bullets) {

    @Override
    public TankModel mainPlayer() {
        return mainPlayer;
    }

    @Override
    public List<Position> blocks() {
        return blocks;
    }

    @Override
    public List<TankModel> enemies() {
        return enemies;
    }

    @Override
    public List<Position> bullets() {
        return bullets;
    }

    @Override
    public Position base() {
        return base;
    }
}
