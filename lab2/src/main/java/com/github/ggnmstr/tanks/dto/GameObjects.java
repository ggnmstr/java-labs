package com.github.ggnmstr.tanks.dto;

import java.util.List;


public record GameObjects(TankObject mainPlayer, BlockObject base, List<BlockObject> blocks,
                          List<TankObject> enemies, List<BulletObject> bullets) {

    @Override
    public TankObject mainPlayer() {
        return mainPlayer;
    }

    @Override
    public List<BlockObject> blocks() {
        return blocks;
    }

    @Override
    public List<TankObject> enemies() {
        return enemies;
    }

    @Override
    public List<BulletObject> bullets() {
        return bullets;
    }

    @Override
    public BlockObject base() {
        return base;
    }
}
