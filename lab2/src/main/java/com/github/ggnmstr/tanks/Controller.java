package com.github.ggnmstr.tanks;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements KeyListener {
    private final JLabel text;

    public Controller(JLabel label) {
        this.text = label;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        text.setText(String.valueOf(e.getKeyCode()));
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
