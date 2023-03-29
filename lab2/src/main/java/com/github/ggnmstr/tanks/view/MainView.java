package com.github.ggnmstr.tanks.view;

import com.github.ggnmstr.tanks.GVData;
import com.github.ggnmstr.tanks.model.Tank;
import com.github.ggnmstr.tanks.presenter.Presenter;

public interface MainView {
    void prepareGame();

    void update(GVData data);

    void setPresenter(Presenter presenter);
}
