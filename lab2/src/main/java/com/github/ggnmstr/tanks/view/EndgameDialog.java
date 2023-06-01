package com.github.ggnmstr.tanks.view;

import com.github.ggnmstr.tanks.util.ScoreManager;

import javax.swing.*;
import java.awt.*;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class EndgameDialog {

    public static JDialog getEndgameDialog(Frame owner, String title, int score){
        JDialog dialog = new JDialog(owner,title);
        dialog.setPreferredSize(new Dimension(420,300));
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel scoreLabel = new JLabel("Your value: " + score,SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Source Code Pro",Font.PLAIN,18));

        JLabel nameLabel = new JLabel("Enter your playerName below: ",SwingConstants.CENTER);
        nameLabel.setFont(new Font("Source Code Pro",Font.PLAIN,18));

        JTextField nameField = new JTextField(30);
        nameField.addActionListener(e -> {
            // CR: check that name is not empty
            ScoreManager.getInstance().addScore(nameField.getText(),score);
            dialog.dispose();
        });

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.add(scoreLabel);
        panel.add(nameLabel);
        panel.add(nameField);

        dialog.add(panel);
        dialog.pack();
        return dialog;
    }
}
