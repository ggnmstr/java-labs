package com.github.ggnmstr.tanks.dto;

import com.github.ggnmstr.tanks.model.*;

import java.util.List;

// CR: naming
public record GameObjects(Tank mainPlayer, Block base, List<Block> blocks,
                          List<Tank> enemies, List<Bullet> bullets) {

    @Override
    public Tank mainPlayer() {
        return mainPlayer;
    }

    @Override
    public List<Block> blocks() {
        return blocks;
    }

    @Override
    public List<Tank> enemies() {
        return enemies;
    }

    @Override
    public List<Bullet> bullets() {
        return bullets;
    }

    @Override
    public Block base() {
        return base;
    }
}
