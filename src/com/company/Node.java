package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Node extends JButton {

    Node parent;
    int column;
    int row;
    int gCost;
    int hCost;
    int fCost;
    boolean start;
    boolean goal;
    boolean solid;
    boolean opened;
    boolean checked;

    public void setAsStart() {
        setBackground(Color.blue);
        setForeground(Color.white);
        setText("Start");
        start = true;
    }

    public void setAsGoal() {
        setBackground(Color.red);
        setForeground(Color.white);
        setText("Goal");
        goal = true;
    }

    public void setAsSolid() {
        setBackground(Color.black);
        setForeground(Color.white);
        solid = true;
    }

    public void setAsOpen() {
        opened = true;
    }

    public void setAsChecked() {
        if(!start && !goal) {
            setBackground(Color.orange);
            setForeground(Color.black);
        }
    }

    public void setAsPath() {
        setOpaque(true);
        setBackground(Color.green);
        setForeground(Color.green);
    }

    public Node(int column, int row) {
        this.column = column;
        this.row = row;

        setBackground(Color.WHITE);
        setForeground(Color.black);
        setOpaque(true);

        addActionListener(e -> {
            if(!goal && !start)
            setBackground(Color.orange);
        });

    }

}
