package com.github.ggnmstr.tanks.view;

import com.github.ggnmstr.tanks.GVData;
import com.github.ggnmstr.tanks.model.*;
import com.github.ggnmstr.tanks.presenter.Presenter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class GameView extends JPanel {

    BufferedImage playerTankImage;

    private GVData gvData;
    private Presenter presenter;

    public GameView() {
        super();
        setBackground(Color.darkGray);
        setFocusable(true);
        loadResources();
        //setKeyBindings();
        setVisible(true);
    }

    private void setKeyBindings() {
        // CR: add GameActionListener interface { move(Direction); shoot() }
        // CR: call presenter as listener
        ActionMap actionMap = getActionMap();
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);

        inputMap.put(KeyStroke.getKeyStroke("W"),"up");
        actionMap.put("up",new KeyAction("move up"));

        inputMap.put(KeyStroke.getKeyStroke("A"),"left");
        actionMap.put("left",new KeyAction("move left"));

        inputMap.put(KeyStroke.getKeyStroke("S"),"down");
        actionMap.put("down",new KeyAction("move down"));

        inputMap.put(KeyStroke.getKeyStroke("D"),"right");
        actionMap.put("right",new KeyAction("move right"));

        inputMap.put(KeyStroke.getKeyStroke("SPACE"),"shoot");
        actionMap.put("shoot",new KeyAction("shoot"));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setColor(Color.RED);
        for (GameObject x : gvData.enemies()){
            drawObject(g2d,x);
        }
        g2d.setColor(Color.ORANGE);
        for (GameObject x : gvData.blocks()){
            g2d.setColor(Color.ORANGE);
            if (x == gvData.base()) g2d.setColor(Color.CYAN);
            drawObject(g2d,x);
        }
        g2d.setColor(Color.LIGHT_GRAY);
        for (GameObject x : gvData.bullets()){
            drawObject(g2d,x);
        }
        g2d.setColor(Color.GREEN);
        drawMainPlayer(g, gvData.mainPlayer());
    }

    private void drawMainPlayer(Graphics g, GameObject x) {
        g.drawImage(playerTankImage,x.getxPos(),x.getyPos(),x.getWidth(),x.getHeight(),null);
    }

    private void drawObject(Graphics g, GameObject x) {
        g.drawRect(x.getxPos(),x.getyPos(),x.getWidth(),x.getHeight());
        g.fillRect(x.getxPos(),x.getyPos(),x.getWidth(),x.getHeight());

    }

    private void loadResources(){
        URL path;
        path = Thread.currentThread().getContextClassLoader().getResource("textures/tanktexture.png");
        try {
            playerTankImage = ImageIO.read(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    public void setGVData(GVData data) {
        this.gvData = data;
    }

    private class KeyAction extends AbstractAction {
        public KeyAction(String actionCommand) {
            putValue(ACTION_COMMAND_KEY, actionCommand);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvt) {
            //presenter.responseToKey(actionEvt.getActionCommand());
        }
    }
}
