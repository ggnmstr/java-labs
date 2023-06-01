package com.github.ggnmstr.tanks.view;


import javax.swing.*;
import java.awt.*;

public class GameStatMenu extends JPanel {

    private JLabel enemiesLabel;
    private JLabel scoreLabel;

    private JLabel hpLabel;

    public GameStatMenu() {
        super();
        enemiesLabel = new JLabel();
        scoreLabel = new JLabel();
        hpLabel = new JLabel();
        setBackground(Color.GRAY);
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        enemiesLabel.setText("Enemies left: " );
        enemiesLabel.setFont(new Font("Source Code Pro",Font.PLAIN,18));

        scoreLabel.setText("Score: " );
        scoreLabel.setFont(new Font("Source Code Pro",Font.PLAIN,18));

        hpLabel.setText("HP Left: ");
        hpLabel.setFont(new Font("Source Code Pro",Font.PLAIN,18));


        enemiesLabel.setVisible(true);
        hpLabel.setVisible(true);
        scoreLabel.setVisible(true);


        add(scoreLabel);
        add(enemiesLabel);
        add(hpLabel);


        setVisible(true);
    }

    public void update(int hp, int left, int score) {
        hpLabel.setText("HP Left: " + hp);
        enemiesLabel.setText("Enemies left: " + left);
        scoreLabel.setText("Score: " + score);
    }
}
