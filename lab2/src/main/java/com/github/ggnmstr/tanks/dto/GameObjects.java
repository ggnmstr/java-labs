package com.github.ggnmstr.tanks.dto;

import java.util.List;



public record GameObjects(TankModel mainPlayer, Position base, List<BlockTO> blocks,
                          List<TankModel> enemies, List<Position> bullets) {

    @Override
    public TankModel mainPlayer() {
        return mainPlayer;
    }

    @Override
    public List<BlockTO> blocks() {
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
