package com.github.ggnmstr.tanks.model;

import com.github.ggnmstr.tanks.dto.BulletObject;
import com.github.ggnmstr.tanks.util.Direction;

public class Bullet extends GamePrimitive {
    private final Direction direction;
    private final Tank owner;

    public Bullet(int startX, int startY, Direction direction, Tank tank) {
        if (direction == Direction.DOWN || direction == Direction.UP) {
            this.width = GameParameters.BULLETSHORT;
            this.height = GameParameters.BULLETLONG;
        } else {
            this.width = GameParameters.BULLETLONG;
            this.height = GameParameters.BULLETSHORT;
        }
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
            case UP -> yPos -= GameParameters.BULLETSPEED;
            case DOWN -> yPos += GameParameters.BULLETSPEED;
            case LEFT -> xPos -= GameParameters.BULLETSPEED;
            case RIGHT -> xPos += GameParameters.BULLETSPEED;
        }
    }

    public static Bullet explosionFrom(Bullet bullet) {
        int x = bullet.xPos, y = bullet.yPos, w = 1, h = 1;
        switch (bullet.getDirection()) {
            case UP -> {
                x -= 2 * GameParameters.BLOCKWIDTH;
                x += 0.5 * GameParameters.BULLETSHORT;
                w = 4 * GameParameters.BLOCKWIDTH;
            }
            case DOWN -> {
                y += GameParameters.BULLETLONG;
                x -= 2 * GameParameters.BLOCKWIDTH;
                x += 0.5 * GameParameters.BULLETSHORT;
                w = 4 * GameParameters.BLOCKWIDTH;
            }
            case LEFT -> {
                y -= 2 * GameParameters.BLOCKHEIGHT;
                y += 0.5 * GameParameters.BULLETSHORT;
                h = 4 * GameParameters.BLOCKHEIGHT;
            }
            case RIGHT -> {
                x += GameParameters.BULLETLONG;
                y -= 2 * GameParameters.BLOCKHEIGHT;
                y += 0.5 * GameParameters.BULLETSHORT;
                h = 4 * GameParameters.BLOCKHEIGHT;
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
}
