package com.github.ggnmstr.tanks.view;

import com.github.ggnmstr.tanks.model.GameParameters;

import javax.swing.*;
import java.awt.*;

public class GameStatMenu extends JPanel {

    private JLabel enemiesLabel;
    private JLabel scoreLabel;
    public GameStatMenu(){
        super();
        enemiesLabel = new JLabel();
        scoreLabel = new JLabel();
        setBackground(Color.PINK);
        setSize(new Dimension(100, GameParameters.FIELDHEIGHT));
        add(scoreLabel);
        add(enemiesLabel);
        setVisible(true);
    }

    public void update(int left, int score){
        enemiesLabel.setText(Integer.toString(left));
        scoreLabel.setText(Integer.toString(score));
    }



}
