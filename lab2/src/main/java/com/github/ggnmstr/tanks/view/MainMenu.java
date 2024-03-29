package com.github.ggnmstr.tanks.view;

import com.github.ggnmstr.tanks.dto.GameObjects;
import com.github.ggnmstr.tanks.presenter.Presenter;
import com.github.ggnmstr.tanks.util.Direction;
import com.github.ggnmstr.tanks.util.ScoreManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

public class MainMenu extends JFrame implements KeyListener {
    private  Presenter presenter;

    private GameView gameView;

    private GameStatMenu statMenu;

    public MainMenu(){
        super("Tanks");
        this.setPreferredSize(new Dimension(1000,900));
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ScoreManager.getInstance().saveToFile();
                System.exit(0);
            }
        });
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setupMenuBar();
        this.addKeyListener(this);
        setLayout(new BorderLayout());
        this.pack();
        this.setVisible(true);

    }

    private void setupMenuBar(){
        JMenuBar menuBar = new JMenuBar();

        JMenuItem newGameMenu = new JMenu("New Game");

        int lvlNum = 1;
        while (true){
            String viewString = "Level " + lvlNum;
            String lvlString = "level"+lvlNum;
            URL lvlURL = Thread.currentThread().getContextClassLoader().getResource("maps/"+lvlString+".json");
            if (lvlURL == null) break;
            JMenuItem item = new JMenuItem(viewString);
            item.addActionListener(e -> presenter.startNewGame(lvlString));
            newGameMenu.add(item);
            lvlNum++;
        }

        JMenuItem highScoresMenu = new JMenuItem("High Scores");
        JMenuItem aboutMenu = new JMenuItem("About");
        JMenuItem exitMenu = new JMenuItem("Exit");


        highScoresMenu.addActionListener(e -> showHighScores());
        aboutMenu.addActionListener(e -> launchAboutMenu());
        exitMenu.addActionListener(e -> {
            ScoreManager.getInstance().saveToFile();
            System.exit(0);
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
        if (e.getKeyCode() == KeyEvent.VK_SPACE){
            presenter.shoot();
        } else {
            presenter.setDirection(keyToDirection(e.getKeyCode()));
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        presenter.stopMovementDirection(keyToDirection(e.getKeyCode()));
    }

    private Direction keyToDirection(int keycode){
        switch (keycode){
            case KeyEvent.VK_W -> {
                return Direction.UP;
            }
            case KeyEvent.VK_A -> {
                return Direction.LEFT;
            }
            case KeyEvent.VK_S -> {
                return Direction.DOWN;
            }
            case KeyEvent.VK_D -> {
                return Direction.RIGHT;
            }
        }
        return Direction.NONE;
    }
}
