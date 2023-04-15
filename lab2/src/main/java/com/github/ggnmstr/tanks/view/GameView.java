package com.github.ggnmstr.tanks.view;

import com.github.ggnmstr.tanks.GVData;
import com.github.ggnmstr.tanks.model.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class GameView extends JPanel {
    private List<BufferedImage> playerImages;
    private List<BufferedImage> enemiesImages;
    private BufferedImage treesImage;
    private BufferedImage brickImage;
    private BufferedImage baseImage;
    private BufferedImage metalImage;

    private GVData gvData;

    public GameView() {
        super();
        setBackground(Color.darkGray);
        setFocusable(true);
        loadResources();
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        drawTank(g2d,playerImages,gvData.mainPlayer());
        //drawTank(g2d,playerImage,gvData.mainPlayer());
        //drawImage(g, playerImage,gvData.mainPlayer());
        for (Tank x : gvData.enemies()){
            //drawImage(g,enemyImage,x);
            drawTank(g2d,enemiesImages,x);
        }
        for (Block x : gvData.blocks()){
            if (x == gvData.base()){
                drawImage(g,baseImage,x);
                continue;
            }
            if (x.isInvincible()){
                drawImage(g,metalImage,x);
            } else if (x.isTransparent()) {
                drawImage(g,treesImage,x);
            } else {
                drawImage(g,brickImage,x);
            }

        }
        for (GameObject x : gvData.bullets()){
            drawObject(g2d,x);
        }
    }

    private void drawTank(Graphics2D g2d, List<BufferedImage> textures, Tank tank){
        int direction = tank.getLastMove().ordinal();
        g2d.drawImage(textures.get(direction),tank.getxPos(),tank.getyPos(),
                tank.getWidth(),tank.getHeight(),null);
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
        loadTankImages();
        URL brickpath = Thread.currentThread().getContextClassLoader().getResource("textures/brick.png");
        URL basepath = Thread.currentThread().getContextClassLoader().getResource("textures/base.png");
        URL metalpath = Thread.currentThread().getContextClassLoader().getResource("textures/metal.png");
        URL treespath = Thread.currentThread().getContextClassLoader().getResource("textures/trees.png");
        try {
            brickImage = ImageIO.read(brickpath);
            baseImage = ImageIO.read(basepath);
            metalImage = ImageIO.read(metalpath);
            treesImage = ImageIO.read(treespath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadTankImages(){
        playerImages = new ArrayList<>();
        enemiesImages = new ArrayList<>();
        URL pd1 = Thread.currentThread().getContextClassLoader().getResource("textures/player/pd1.png");
        //URL pd2 = Thread.currentThread().getContextClassLoader().getResource("textures/player/pd2.png");
        URL pl1 = Thread.currentThread().getContextClassLoader().getResource("textures/player/pl1.png");
        //URL pl2 = Thread.currentThread().getContextClassLoader().getResource("textures/player/pl2.png");
        URL pr1 = Thread.currentThread().getContextClassLoader().getResource("textures/player/pr1.png");
        //URL pr2 = Thread.currentThread().getContextClassLoader().getResource("textures/player/pr2.png");
        URL pu1 = Thread.currentThread().getContextClassLoader().getResource("textures/player/pu1.png");
        //URL pu2 = Thread.currentThread().getContextClassLoader().getResource("textures/player/pu2.png");

        URL ed1 = Thread.currentThread().getContextClassLoader().getResource("textures/enemy/ed1.png");
        URL el1 = Thread.currentThread().getContextClassLoader().getResource("textures/enemy/el1.png");
        URL er1 = Thread.currentThread().getContextClassLoader().getResource("textures/enemy/er1.png");
        URL eu1 = Thread.currentThread().getContextClassLoader().getResource("textures/enemy/eu1.png");
        try {
            playerImages.add(ImageIO.read(pd1));
            //playerImages.add(ImageIO.read(pd2));
            playerImages.add(ImageIO.read(pl1));
            //playerImages.add(ImageIO.read(pl2));
            playerImages.add(ImageIO.read(pr1));
            //playerImages.add(ImageIO.read(pr2));
            playerImages.add(ImageIO.read(pu1));
            //playerImages.add(ImageIO.read(pu2));

            enemiesImages.add(ImageIO.read(ed1));
            enemiesImages.add(ImageIO.read(el1));
            enemiesImages.add(ImageIO.read(er1));
            enemiesImages.add(ImageIO.read(eu1));
        } catch (IOException e){
            throw new RuntimeException(e);
        }

    }

    public void setGVData(GVData data) {
        this.gvData = data;
    }
}
