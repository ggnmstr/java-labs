package com.github.ggnmstr.tanks.view;

import com.github.ggnmstr.tanks.presenter.Presenter;

public interface TanksView {
    void run();

    void setPresenter(Presenter presenter);
}
