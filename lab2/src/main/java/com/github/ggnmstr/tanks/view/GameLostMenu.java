package com.github.ggnmstr.tanks.view;

import com.github.ggnmstr.tanks.presenter.Presenter;
import com.github.ggnmstr.tanks.util.HighScoresWorker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.ThreadLocalRandom;

public class GameLostMenu extends JDialog {
    public GameLostMenu(Frame owner, String title){
        super(owner, title);
        this.setPreferredSize(new Dimension(420,300));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JLabel label = new JLabel("Enter your name below: ",SwingConstants.CENTER);
        label.setFont(new Font("Source Code Pro",Font.PLAIN,18));
        JTextField nameField = new JTextField(30);
        // TODO : work on this so this dialog can be closed properly using keyboard / button
        nameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //HighScoresWorker.writeScore(nameField.getText(),100);
                HighScoresWorker.writeScore(nameField.getText(), 100    );

            }
        });
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(nameField);

        this.add(panel);

        this.pack();
    }
}
