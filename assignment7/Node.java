package assignment7;

import javafx.scene.layout.GridPane;

import java.awt.*;

public class Node extends GridPane {

    private String name;
    private Point point;
    private Rectangle r = new Rectangle();

    public Node(String s, Point p){

        name = s;
        point = p;
    }

    public String getName(){

        return name;
    }

    public Point getPoint(){

        return point;
    }

    public void draw(Graphics graphics){

        graphics.setColor(Color.BLUE);
        graphics.fillOval(point.x, point.y, 20, 20);
    }
}
