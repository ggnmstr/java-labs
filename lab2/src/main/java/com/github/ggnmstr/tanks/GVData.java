package com.github.ggnmstr.tanks;

import com.github.ggnmstr.tanks.model.*;

import java.util.List;

public record GVData(GameObject mainPlayer, List<Block> blocks,
                     List<Tank> enemies, List<Bullet> bullets) {

    @Override
    public GameObject mainPlayer() {
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
}
