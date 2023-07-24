package com.company;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DemoPanel extends JPanel {
    //Screen Settings
    final int maxColumn = 15;
    final int maxRow = 10;
    final int nodeSize = 70;    //Node is individual tile
    final int screenWidth = nodeSize * maxColumn;
    final int screenHeight = nodeSize * maxRow;

    Node[][] nodes = new Node[maxColumn][maxRow];

    Node startNode, goalNode, currentNode;
    ArrayList<Node> openList = new ArrayList<>();
    ArrayList<Node> checkedList = new ArrayList<>();

    boolean goalReached = false;
    int step = 0;

    public DemoPanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setLayout(new GridLayout(maxRow, maxColumn));
        this.addKeyListener(new KeyHandler(this));
        this.setFocusable(true);

        int col = 0;
        int row= 0;
        while(col < maxColumn && row < maxRow) {

            nodes[col][row] = new Node(col, row);
            this.add(nodes[col][row]);

            col++;
            if(col == maxColumn) {
                col = 0;
                row++;
            }
        }

        setStartNode(3, 6);
        setGoalNode(11, 3 );

        //Place the obstacle node
        setSolidNode(10,2);
        setSolidNode(10,3);
        setSolidNode(10,4);
        setSolidNode(10,5);
        setSolidNode(10,6);
        setSolidNode(10,7);
        setSolidNode(6,2);
        setSolidNode(7,2);
        setSolidNode(8,2);
        setSolidNode(9,2);
        setSolidNode(11,7);
        setSolidNode(6,1);

        setCostOnNodes();
    }

    public void search() {
        if (!goalReached && step < 300) {
            int col = currentNode.column;
            int row = currentNode.row;

            currentNode.setAsChecked();
            checkedList.add(currentNode);
            openList.remove(currentNode);

            if (row-1 >= 0)
                openNode(nodes[col][row-1]);

            if (col-1 >= 0)
                openNode(nodes[col-1][row]);

            if (row+1 < maxRow)
                openNode(nodes[col][row+1]);

            if (col+1 < maxColumn)
                openNode(nodes[col+1][row]);

            int bestNodeIndex = 0;
            int bestNodeFCost = 999;

            for (int i = 0; i<openList.size(); i++) {
                if (openList.get(i).fCost < bestNodeFCost) {
                    bestNodeIndex = i;
                    bestNodeFCost = openList.get(i).fCost;
                } else if (openList.get(i).fCost == bestNodeFCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }

            currentNode = openList.get(bestNodeIndex);

            if (currentNode == goalNode) {
                goalReached = true;
                trackThePath();
            }

        }

        step++;
    }

    public void autoSearch() {
        while (!goalReached && step < 300) {
            int col = currentNode.column;
            int row = currentNode.row;

            currentNode.setAsChecked();
            checkedList.add(currentNode);
            openList.remove(currentNode);

            if (row-1 >= 0)
                openNode(nodes[col][row-1]);

            if (col-1 >= 0)
                openNode(nodes[col-1][row]);

            if (row+1 < maxRow)
                openNode(nodes[col][row+1]);

            if (col+1 < maxColumn)
                openNode(nodes[col+1][row]);

            int bestNodeIndex = 0;
            int bestNodeFCost = 999;

            for (int i = 0; i<openList.size(); i++) {
                if (openList.get(i).fCost < bestNodeFCost) {
                    bestNodeIndex = i;
                    bestNodeFCost = openList.get(i).fCost;
                } else if (openList.get(i).fCost == bestNodeFCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }

            currentNode = openList.get(bestNodeIndex);

            if (currentNode == goalNode) {
                goalReached = true;
            }

        }
        step++;
    }

    private void trackThePath() {

        Node current = goalNode;

        while (current != startNode) {

            current = current.parent;

            if(current != startNode) {
                current.setAsPath();
            }
        }

    }

    private void openNode(Node node) {
        if(!node.opened && !node.checked && !node.solid) {
            node.setAsOpen();
            node.parent = currentNode;
            openList.add(node);
        }
    }

    private void setCostOnNodes() {
        int col = 0;
        int row= 0;
        while(col < maxColumn && row < maxRow) {

            getCost(nodes[col][row]);

            col++;
            if(col == maxColumn) {
                col = 0;
                row++;
            }
        }
    }

    private void getCost(Node node) {
        //Get G Cost (Distance between start and current)
        int xDistance = Math.abs(node.column - startNode.column);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        //Get H Cost (Distance between goal and current)
        xDistance = Math.abs(node.column - goalNode.column);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        // Get F cost
        node.fCost = node.gCost + node.hCost;

        if(node != startNode && node != goalNode) {
            node.setText(
                    "<html>H: " + node.hCost + "<br>G:" + node.gCost + "<br>F:" + node.hCost + "</html>"
            );
        }
    }

    public void setSolidNode(int col, int row) {
        nodes[col][row].setAsSolid();
    }

    private void setStartNode(int col, int row) {
        nodes[col][row].setAsStart();
        startNode = nodes[col][row];
        currentNode = startNode;
    }

    private void setGoalNode(int col, int row) {
        nodes[col][row].setAsGoal();
        goalNode = nodes[col][row];
    }
}
