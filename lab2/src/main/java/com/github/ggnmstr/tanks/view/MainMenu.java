package com.github.ggnmstr.tanks.view;

import com.github.ggnmstr.tanks.dto.GameObjects;
import com.github.ggnmstr.tanks.presenter.Presenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MainMenu extends JFrame implements KeyListener {
    private  Presenter presenter;

    private GameView gameView;

    private GameStatMenu statMenu;
    // CR: move to separate class
    private JMenuBar menuBar;
    private JMenuItem newGameMenu, highScoresMenu, aboutMenu, exitMenu;
    public MainMenu(){
        super("Tanks");
        this.setPreferredSize(new Dimension(1000,900));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setupMenuBar();
        this.addKeyListener(this);
        setLayout(new BorderLayout());
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
        highScoresMenu.addActionListener(e -> showHighScores());
        aboutMenu.addActionListener(e -> launchAboutMenu());
        exitMenu.addActionListener(e -> {
            System.exit(0);
            // CR: call record manager
        });

        menuBar.add(newGameMenu);
        menuBar.add(highScoresMenu);
        menuBar.add(aboutMenu);
        menuBar.add(exitMenu);

        this.setJMenuBar(menuBar);

    }

    private void showHighScores() {
        HighScoresDialog highScores = new HighScoresDialog(this,"High Scores");
        highScores.setVisible(true);
    }

    public void prepareGame() {
        if (gameView == null){
            gameView = new GameView();
            statMenu = new GameStatMenu();
            add(gameView);
            add(statMenu,BorderLayout.LINE_END);
            pack();
        }

    }


    public void updateMenu(int hp, int enemiesLeft, int score){
        statMenu.update(hp, enemiesLeft,score);
    }

    public void update(GameObjects data) {
        gameView.update(data);
        gameView.repaint();
    }

    public void launchAboutMenu() {
        AboutDialogMenu dialogMenu = new AboutDialogMenu(this,"About");
        dialogMenu.setVisible(true);
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void launchEndgameMenu(String title, int score) {
        JDialog endgameDialog = EndgameDialog.getEndgameDialog(this,title,score);
        endgameDialog.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        presenter.responseToKey(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        presenter.stopMovementKey(e.getKeyCode());
    }
}
