package com.github.ggnmstr.tanks.view;

import com.github.ggnmstr.tanks.dto.BlockObject;
import com.github.ggnmstr.tanks.dto.GameObjects;
import com.github.ggnmstr.tanks.dto.Position;
import com.github.ggnmstr.tanks.dto.TankObject;

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

    private GameObjects gameObjects;

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
        Graphics2D g2d = (Graphics2D) g;
        drawTank(g2d, playerImages, gameObjects.mainPlayer());
        for (TankObject x : gameObjects.enemies()) {
            drawTank(g2d, enemiesImages, x);
        }
        BlockObject baseObject = gameObjects.base();
        drawImage(g, baseImage, baseObject.x(), baseObject.y(), baseObject.width(), baseObject.height());
        for (BlockObject block : gameObjects.blocks()) {
            // TODO: bushes gone :(
            if (block.isDestructible()) {
                drawImage(g, brickImage, block.x(), block.y(), block.width(), block.height());
            }
            else {
                drawImage(g, metalImage, block.x(), block.y(), block.width(), block.height());
            }
        }
        for (Position x : gameObjects.bullets()) {
            drawObject(g2d, x);
        }
    }

    private void drawTank(Graphics2D g2d, List<BufferedImage> textures, TankObject tank) {
        int direction = tank.rotation().ordinal();
        g2d.drawImage(textures.get(direction), tank.x(), tank.y(),
                tank.width(), tank.height(), null);
    }

    private void drawImage(Graphics g, BufferedImage image, int x, int y, int width, int height) {
        g.drawImage(image, x, y, width, height, null);
    }

    private void drawObject(Graphics g, Position x) {
        g.drawRect(x.x(), x.y(), x.width(), x.height());
        g.fillRect(x.x(), x.y(), x.width(), x.height());

    }

    private void loadResources() {
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

    private void loadTankImages() {
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
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void update(GameObjects data) {
        this.gameObjects = data;
    }
}
