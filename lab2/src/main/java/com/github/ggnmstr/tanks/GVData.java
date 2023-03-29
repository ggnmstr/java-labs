package com.github.ggnmstr.tanks;

import com.github.ggnmstr.tanks.model.GameObject;

import java.util.ArrayList;
import java.util.List;

public class GVData {
    public List<GameObject> objlist;
    public GVData(List<GameObject> objects){
        this.objlist = objects;
    }

    public List<GameObject> getList(){
        return objlist;
    }

}
