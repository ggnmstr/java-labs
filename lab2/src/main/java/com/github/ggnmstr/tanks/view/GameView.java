package com.github.ggnmstr.tanks.view;

import com.github.ggnmstr.tanks.GVData;
import com.github.ggnmstr.tanks.model.*;
import com.github.ggnmstr.tanks.presenter.Presenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameView extends JPanel {

    private GVData gvData;
    private Presenter presenter;

    public GameView() {
        super();
        setBackground(Color.darkGray);
        setFocusable(true);
        setKeyBindings();
        System.out.println(getSize());
        setVisible(true);
    }

    private void setKeyBindings() {
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
        for (GameObject x : gvData.objList){
            if (x instanceof Tank && !(x instanceof EnemyTank)) g2d.setColor(Color.GREEN);
            else if (x instanceof Bullet) g2d.setColor(Color.LIGHT_GRAY);
            else if (x instanceof EnemyTank) g2d.setColor(Color.RED);
            else if (x instanceof Block) g2d.setColor(Color.ORANGE);
            else if (x instanceof Base) g2d.setColor(Color.CYAN);
            drawObject(g2d,x);
        }
    }

    private void drawObject(Graphics g, GameObject x){
        g.drawRect(x.getxPos(),x.getyPos(),x.getWidth(),x.getHeight());
        g.fillRect(x.getxPos(),x.getyPos(),x.getWidth(),x.getHeight());

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
            presenter.responseToKey(actionEvt.getActionCommand());
        }
    }
}
