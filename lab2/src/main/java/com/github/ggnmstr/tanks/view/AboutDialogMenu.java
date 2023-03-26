package com.github.ggnmstr.tanks.view;

import javax.swing.*;
import java.awt.*;

public class AboutDialogMenu extends JDialog {

    private final String aboutString = """
            <html>
            NSU FIT OOP Course Part 2<br/>
            Made with Java Swing<br/>
            Novosibirsk, 2023.
            </html>
            """;

    public AboutDialogMenu(Frame owner, String title) {
        super(owner, title);
        this.setPreferredSize(new Dimension(420,300));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JLabel label = new JLabel(aboutString,SwingConstants.CENTER);
        label.setFont(new Font("Source Code Pro",Font.PLAIN,18));
        this.add(label);
        this.pack();
    }
}
