package com.github.ggnmstr.tanks.view;

import com.github.ggnmstr.tanks.presenter.Presenter;
import com.github.ggnmstr.tanks.util.HighScoresWorker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.ThreadLocalRandom;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class GameLostMenu {

    public static JDialog getLostDialog(Frame owner, String title){
        JDialog dialog = new JDialog(owner,title);
        dialog.setPreferredSize(new Dimension(420,300));
        dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel label = new JLabel("Enter your name below: ",SwingConstants.CENTER);
        label.setFont(new Font("Source Code Pro",Font.PLAIN,18));

        JTextField nameField = new JTextField(30);
        nameField.addActionListener(e -> {
            HighScoresWorker.writeScore(nameField.getText(), 100);
            dialog.dispose();
        });

        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(nameField);

        dialog.add(panel);
        dialog.pack();
        return dialog;
    }
}
