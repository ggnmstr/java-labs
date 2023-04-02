package com.github.ggnmstr.tanks.view;

import com.github.ggnmstr.tanks.GVData;
import com.github.ggnmstr.tanks.presenter.Presenter;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    //  Exit, About, New Game, High Scores.
    private  Presenter presenter;

    private GameView gameView;
    private JMenuBar menuBar;
    private JMenuItem newGameMenu, highScoresMenu, aboutMenu, exitMenu;
    public MainMenu(){
        super("Tanks");
        this.setPreferredSize(new Dimension(900,900));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setupMenuBar();

        this.pack();
        this.setVisible(true);

    }

    private void setupMenuBar(){
        menuBar = new JMenuBar();

        newGameMenu = new JMenuItem("New Game");
        highScoresMenu = new JMenuItem("High Scores");
        aboutMenu = new JMenuItem("About");
        exitMenu = new JMenuItem("Exit");


        newGameMenu.addActionListener(e -> presenter.startNewGame());
        aboutMenu.addActionListener(e -> launchAboutMenu());
        exitMenu.addActionListener(e -> System.exit(0));

        menuBar.add(newGameMenu);
        menuBar.add(highScoresMenu);
        menuBar.add(aboutMenu);
        menuBar.add(exitMenu);

        this.setJMenuBar(menuBar);

    }

    public void prepareGame() {
        GameView gameView = new GameView();
        this.gameView = gameView;
        gameView.setPresenter(presenter);
        add(gameView);
        pack();

    }



    public void update(GVData data) {
        gameView.setGVData(data);
        gameView.repaint();
    }

    public void launchAboutMenu() {
        AboutDialogMenu dialogMenu = new AboutDialogMenu(this,"About");
        dialogMenu.setVisible(true);
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void launchLostMenu() {
        GameLostMenu lostMenu = new GameLostMenu(this,"You lost!");
        lostMenu.setVisible(true);
    }
}
