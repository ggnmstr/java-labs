package com.github.ggnmstr.tanks.view;

import com.github.ggnmstr.tanks.model.Tank;
import com.github.ggnmstr.tanks.presenter.Presenter;

public interface MainView {
    void prepareGame();

    void update(Tank tank);

    void setPresenter(Presenter presenter);
}
