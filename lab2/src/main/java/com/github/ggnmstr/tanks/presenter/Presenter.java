package com.github.ggnmstr.tanks.presenter;

import com.github.ggnmstr.tanks.view.AboutDialogMenu;
import com.github.ggnmstr.tanks.view.MainMenu;
import com.github.ggnmstr.tanks.view.TanksView;

public class Presenter {
    private TanksView view;

    public void setView(TanksView view){
        this.view = view;
    }

    public void run(){
        view.run();
    }
    
    public void startNewGame(){
        
    }

    public void launchAboutMenu() {
        AboutDialogMenu dialogMenu = new AboutDialogMenu((MainMenu)view,"About");
        dialogMenu.setVisible(true);
    }
}
