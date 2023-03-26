package com.github.ggnmstr.tanks.view;


import com.github.ggnmstr.tanks.Controller;
import com.github.ggnmstr.tanks.presenter.Presenter;

import java.awt.*;
import javax.swing.*;

// THIS CLASS IS CREATED FOR EXPERIMENTAL PURPOSES ONLY
public class testWindow extends JFrame implements TanksView {

    public testWindow(){
        super("Tanks");
    }

    private static void create() {

        JFrame frame = new JFrame("Tanks");
        frame.setPreferredSize(new Dimension(960,720));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel emptyLabel = new JLabel("abcd");
        emptyLabel.setPreferredSize(new Dimension(175, 100));
        frame.getContentPane().add(emptyLabel, BorderLayout.CENTER);
        frame.addKeyListener(new Controller(emptyLabel));

        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(testWindow::create);
    }

    @Override
    public void run() {

    }

    @Override
    public void setPresenter(Presenter presenter) {

    }
}
