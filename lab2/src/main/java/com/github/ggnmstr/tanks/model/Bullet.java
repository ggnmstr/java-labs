package com.github.ggnmstr.tanks.model;

import com.github.ggnmstr.tanks.dto.BulletObject;
import com.github.ggnmstr.tanks.util.Direction;

public class Bullet extends GamePrimitive {
    private final Direction direction;
    private final Tank owner;

    public Bullet(int startX, int startY, Direction direction, Tank tank) {
        this.width = 10;
        this.height = 10;
        this.xPos = startX;
        this.yPos = startY;
        this.direction = direction;
        this.owner = tank;
    }

    public static Bullet from(BulletObject bulletObject){
        return new Bullet(bulletObject.x(),bulletObject.y(),bulletObject.direction(),null);
    }

    public void move() {
        switch (direction) {
            case UP -> yPos -= 10;
            case DOWN -> yPos += 10;
            case LEFT -> xPos -= 10;
            case RIGHT -> xPos += 10;
        }
    }

    public static Bullet explosionFrom(Bullet bullet, int blocksize) {
        int x = bullet.xPos;
        int y = bullet.yPos;
        int bulletSize = bullet.getBulletSize();
        int w = 1;
        int h = 1;
        switch (bullet.getDirection()) {
            case UP -> {
                x -= 2 * blocksize;
                x += 0.5 * bulletSize;
                w = 4 * blocksize;
            }
            case DOWN -> {
                y += bulletSize;
                x -= 2 * blocksize;
                x += 0.5 * bulletSize;
                w = 4 * blocksize;
            }
            case LEFT -> {
                y -= 2 * blocksize;
                y += 0.5 * bulletSize;
                h = 4 * blocksize;
            }
            case RIGHT -> {
                x += bulletSize;
                y -= 2 * blocksize;
                y += 0.5 * bulletSize;
                h = 4 * blocksize;
            }
        }
        Bullet explosion = new Bullet(x,y,bullet.direction,null);
        explosion.width = w;
        explosion.height = h;
        return explosion;
    }

    public Direction getDirection() {
        return direction;
    }

    public Tank getOwner() {
        return owner;
    }

    public int getBulletSize() {
        return owner.getBulletSize();
    }
}
