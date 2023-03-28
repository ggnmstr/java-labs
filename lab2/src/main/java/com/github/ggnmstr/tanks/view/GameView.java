package com.github.ggnmstr.tanks.view;

import com.github.ggnmstr.tanks.model.Tank;
import com.github.ggnmstr.tanks.presenter.Presenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameView extends JPanel {
    private Color color;
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

    }


    public void update(Tank tank) {
    }





    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Tank tank = presenter.getTank();
        g.setColor(Color.RED);
        g.fillRect(tank.getxPos(),tank.getyPos(),100,100);
        g.drawRect(tank.getxPos(), tank.getyPos(), 100,100);
    }

    private void draw(){

    }


    public void setPresenter(Presenter presenter) {
        this.presenter = presenter;
    }

    private class KeyAction extends AbstractAction {
        public KeyAction(String actionCommand) {
            putValue(ACTION_COMMAND_KEY, actionCommand);
        }

        @Override
        public void actionPerformed(ActionEvent actionEvt) {
            presenter.moveTank(actionEvt.getActionCommand());
        }
    }
}
