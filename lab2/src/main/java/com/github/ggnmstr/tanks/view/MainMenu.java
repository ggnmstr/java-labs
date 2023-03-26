package com.github.ggnmstr.tanks.view;

import com.github.ggnmstr.tanks.presenter.Presenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame implements TanksView {
    //  Exit, About, New Game, High Scores.
    private  Presenter presenter;
    private JMenuBar menuBar;
    private JMenuItem newGameMenu, highScoresMenu, aboutMenu, exitMenu;
    public MainMenu(){
        super("Tanks");
        this.setPreferredSize(new Dimension(960,720));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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
        aboutMenu.addActionListener(e -> presenter.launchAboutMenu());
        exitMenu.addActionListener(e -> System.exit(0));

        menuBar.add(newGameMenu);
        menuBar.add(highScoresMenu);
        menuBar.add(aboutMenu);
        menuBar.add(exitMenu);

        this.setJMenuBar(menuBar);

    }

    @Override
    public void run() {

    }

    @Override
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
}
