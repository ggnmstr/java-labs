package com.github.ggnmstr.tanks.presenter;

import com.github.ggnmstr.tanks.view.TanksView;

public class Presenter {
    private TanksView view;

    public void setView(TanksView view){
        this.view = view;
    }

    public void run(){
        view.run();
    }
}
