package assignment7;

import java.awt.*;

public class Edge {

    Node node1;
    Node node2;
    int weight;

    public Edge(Node n1, Node n2, int w){

        node1 = n1;
        node2 = n2;
        weight = w;
    }

    public void draw(Graphics graphics){

        Point p1 = node1.getPoint();
        Point p2 = node2.getPoint();

        graphics.setColor(Color.lightGray);
        graphics.drawLine(p1.x, p1.y, p2.x, p2.y);
    }
}
