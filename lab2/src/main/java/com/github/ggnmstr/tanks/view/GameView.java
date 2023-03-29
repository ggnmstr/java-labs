package com.github.ggnmstr.tanks.view;

import com.github.ggnmstr.tanks.GVData;
import com.github.ggnmstr.tanks.model.GameObject;
import com.github.ggnmstr.tanks.presenter.Presenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameView extends JPanel {
    private Color color;

    private GVData gvData;
    private Presenter presenter;

    public GameView() {
        super();
        color = Color.ORANGE;
        //setBorder(BorderFactory.createMatteBorder(10,10,10,10,color));
        //setBackground(color);
        setFocusable(true);
        setKeyBindings();

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
        g.setColor(Color.RED);
        for (GameObject x : gvData.objlist){
            drawObject(g,x);
        }
    }

    private void drawObject(Graphics g, GameObject x){
        g.drawRect(x.getxPos(),x.getyPos(),x.getWidth(),x.getHeight());
        g.fillRect(x.getxPos(),x.getyPos(),x.getWidth(),x.getHeight());

    }

    private void draw(){

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
