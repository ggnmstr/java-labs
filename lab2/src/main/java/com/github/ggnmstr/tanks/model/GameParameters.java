package com.github.ggnmstr.tanks.model;

public final class GameParameters {

    public static int FIELDWIDTH = 832;
    public static int FIELDHEIGHT = 832;
    public static int BASEHEIGHT = 60;
    public static int BASEWIDTH = 60;
    public static int TANKSIZE = 60;
    public static int TANKSPEED = 5;

    // CR: remove from model?
    public static int BULLETLONG = 15;
    public static int BULLETSHORT = 10;
    public static int BULLETSPEED = 10;

    public static int BLOCKWIDTH = 15;
    public static int BLOCKHEIGHT = 15;

    // CR: pass from config file
    public static int ENEMYHP = 1;
    // CR: pass from config file
    public static int PLAYERHP = 3;
}
