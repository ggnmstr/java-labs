package com.github.ggnmstr.tanks.view;

import com.github.ggnmstr.tanks.GVData;
import com.github.ggnmstr.tanks.model.*;
import com.github.ggnmstr.tanks.presenter.Presenter;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class GameView extends JPanel {

    private BufferedImage playerImage;
    private BufferedImage brickImage;
    private BufferedImage baseImage;
    private BufferedImage enemyImage;
    private BufferedImage metalImage;

    private GVData gvData;
    //private Presenter presenter;

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
        for (Tank x : gvData.enemies()){
            //drawImage(g,enemyImage,x);
            drawTank(g2d,enemyImage,x);
        }
        for (Block x : gvData.blocks()){
            if (x == gvData.base()){
                drawImage(g,baseImage,x);
                continue;
            }
            if (x.isInvincible()){
                drawImage(g,metalImage,x);
            } else {
                drawImage(g,brickImage,x);
            }

        }
        for (GameObject x : gvData.bullets()){
            drawObject(g2d,x);
        }
        drawTank(g2d,playerImage,gvData.mainPlayer());
        //drawImage(g, playerImage,gvData.mainPlayer());
    }

    private void drawTank(Graphics2D g2d, BufferedImage image, Tank tank){
        double rotation = 0;
        switch (tank.getLastMove()){
            case DOWN -> rotation = Math.toRadians(180);
            case LEFT -> rotation = Math.toRadians(270);
            case RIGHT -> rotation = Math.toRadians(90);
        }
        double locationX = image.getWidth() / 2;
        double locationY = image.getHeight() / 2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotation, locationX, locationY);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
        g2d.drawImage(op.filter(image,null),tank.getxPos(),tank.getyPos(),
                tank.getWidth(), tank.getHeight(), null);

    }

    private void drawImage(Graphics g, BufferedImage image, GameObject gameObject){
        g.drawImage(image,gameObject.getxPos(),gameObject.getyPos(),
                gameObject.getWidth(),gameObject.getHeight(),null);
    }

    private void drawObject(Graphics g, GameObject x) {
        g.drawRect(x.getxPos(),x.getyPos(),x.getWidth(),x.getHeight());
        g.fillRect(x.getxPos(),x.getyPos(),x.getWidth(),x.getHeight());

    }

    private void loadResources(){
        URL tankpath = Thread.currentThread().getContextClassLoader().getResource("textures/tanktexture.png");
        URL brickpath = Thread.currentThread().getContextClassLoader().getResource("textures/brick.png");
        URL basepath = Thread.currentThread().getContextClassLoader().getResource("textures/base.png");
        URL enemypath = Thread.currentThread().getContextClassLoader().getResource("textures/enemy1.png");
        URL metalpath = Thread.currentThread().getContextClassLoader().getResource("textures/metal.png");
        try {
            playerImage = ImageIO.read(tankpath);
            brickImage = ImageIO.read(brickpath);
            baseImage = ImageIO.read(basepath);
            enemyImage = ImageIO.read(enemypath);
            metalImage = ImageIO.read(metalpath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /*
    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }
    */
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
