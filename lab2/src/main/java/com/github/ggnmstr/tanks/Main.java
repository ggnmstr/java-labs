package com.github.ggnmstr.tanks;

import com.github.ggnmstr.tanks.model.BattleField;
import com.github.ggnmstr.tanks.presenter.Presenter;
import com.github.ggnmstr.tanks.view.MainMenu;

public class Main {
    public static void main(String[] args) {
        BattleField field = new BattleField();
        Presenter presenter = new Presenter(field);
        field.setFieldListener(presenter);
        MainMenu mainMenu = new MainMenu();
        presenter.setView(mainMenu);
        mainMenu.setPresenter(presenter);
        presenter.run();
    }
}