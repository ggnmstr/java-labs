package com.github.ggnmstr.tanks.model;

import java.util.concurrent.ThreadLocalRandom;

public class EnemyTank extends Tank {

    private boolean isAlive;
    public EnemyTank(int xPos, int yPos) {
        super(xPos, yPos);
        isAlive = true;
    }

    // CR: rename
    public void makeMove(){
        if (!isAlive) return;
        // THIS THING IS CRAZY LOL
        // CR: https://en.wikipedia.org/wiki/A*_search_algorithm
        int c = ThreadLocalRandom.current().nextInt(1, 100 + 1);
        switch (c){
            case 2 ->{
                this.move(20,0);
            }
            case 3 ->{
                this.move(-20,0);
            }
            case 4 ->{
                this.move(0,20);
            }
            case 5 ->{
                this.move(0,-20);
            }
            case 6 ->{
                this.shoot();
            }
        }
    }

    public void die() {
        isAlive = false;
    }
}
