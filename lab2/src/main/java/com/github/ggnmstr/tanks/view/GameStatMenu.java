package com.github.ggnmstr.tanks.view;

import com.github.ggnmstr.tanks.model.GameParameters;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class GameStatMenu extends JPanel {

    private JLabel enemiesLabel;
    private JLabel scoreLabel;

    public GameStatMenu() {
        super();
        enemiesLabel = new JLabel();
        scoreLabel = new JLabel();
        setBackground(Color.GRAY);

        enemiesLabel.setText("Enemies left: " );
        scoreLabel.setText("Score: " );


        enemiesLabel.setVisible(true);
        scoreLabel.setVisible(true);


        add(scoreLabel);
        add(enemiesLabel);



        setVisible(true);
    }

    public void update(int left, int score) {
        enemiesLabel.setText("Enemies left: " + left);
        scoreLabel.setText("Score: " + score);
    }


}
