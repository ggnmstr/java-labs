package com.github.ggnmstr.tanks;

import com.github.ggnmstr.tanks.model.GameObject;

import java.util.List;

public class GVData {
    public List<GameObject> objList;
    public GVData(List<GameObject> objList){
        this.objList = objList;
    }

    public List<GameObject> getList(){
        return objList;
    }

}
