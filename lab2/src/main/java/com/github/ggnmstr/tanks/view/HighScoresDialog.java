package com.github.ggnmstr.tanks.view;

import com.github.ggnmstr.tanks.util.HighScoresWorker;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HighScoresDialog extends JDialog {

    public HighScoresDialog(Frame owner, String title) {
        super(owner, title);
        this.setPreferredSize(new Dimension(420,600));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        String[] columnNames = {"Player","Score"};
        DefaultTableModel model = new DefaultTableModel(columnNames,0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = new JTable(model);
        List<String> list = HighScoresWorker.getScores();
        for (String rec : list){
            Object[] toadd = rec.split(" : ");
            model.addRow(toadd);
        }
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Source Code Pro",Font.PLAIN,18));
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane);
        this.pack();
    }
}
