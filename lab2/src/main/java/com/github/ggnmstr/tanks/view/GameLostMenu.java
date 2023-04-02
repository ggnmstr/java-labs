package com.github.ggnmstr.tanks.view;

import com.github.ggnmstr.tanks.presenter.Presenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLostMenu extends JDialog {
    public GameLostMenu(Frame owner, String title, Presenter presenter){
        super(owner, title);
        this.setPreferredSize(new Dimension(420,300));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JLabel label = new JLabel("Enter your name below: ",SwingConstants.CENTER);
        label.setFont(new Font("Source Code Pro",Font.PLAIN,18));
        JTextField nameField = new JTextField(30);
        nameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(nameField.getText());
            }
        });
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(nameField);

        this.add(panel);

        this.pack();
    }
}
