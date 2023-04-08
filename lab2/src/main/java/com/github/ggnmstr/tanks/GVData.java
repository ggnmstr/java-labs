package com.github.ggnmstr.tanks;

import com.github.ggnmstr.tanks.model.Block;
import com.github.ggnmstr.tanks.model.Bullet;
import com.github.ggnmstr.tanks.model.EnemyTank;
import com.github.ggnmstr.tanks.model.GameObject;

import java.util.List;

public record GVData(GameObject mainPlayer, List<Block> blocks,
                     List<EnemyTank> enemies, List<Bullet> bullets) {

    @Override
    public GameObject mainPlayer() {
        return mainPlayer;
    }

    @Override
    public List<Block> blocks() {
        return blocks;
    }

    @Override
    public List<EnemyTank> enemies() {
        return enemies;
    }

    @Override
    public List<Bullet> bullets() {
        return bullets;
    }
}
