package com.github.ggnmstr.tanks;

import com.github.ggnmstr.tanks.presenter.Presenter;
import com.github.ggnmstr.tanks.view.MainMenu;

public class Main {
    public static void main(String[] args) {
        Presenter presenter = new Presenter();
        presenter.setView(new MainMenu());
        presenter.run();
    }
}