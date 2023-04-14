package com.github.ggnmstr.tanks.model;

public interface FieldListener {
    void gameLost(int score);
    void gameWon(int score);
    void updateStats(int hp, int enemiesleft, int score);
}
