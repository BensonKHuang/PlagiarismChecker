package assignment7;

import javafx.application.Application;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Graph extends JComponent {

    private Map<Integer, Pair> map = cheaters.treeMap;
    private Set<Node> nodes;
    private Set<Edge> edges;
    private int threshold;

    public Graph(int t){

        nodes = new HashSet<>();
        edges = new HashSet<>();
        threshold = t;

        initializeGraph();
    }


    private void initializeGraph(){

        Random rand = new Random();

        for(Integer i : map.keySet()){

            if(i >= threshold){


                Point p1, p2;

                p1 = new Point(rand.nextInt(800), rand.nextInt(800));
                p2 = new Point(rand.nextInt(800), rand.nextInt(800));

                Node node1 = new Node(map.get(i).get1().toString(), p1);
                Node node2 = new Node(map.get(i).get2().toString(), p2);

                nodes.add(node1);
                nodes.add(node2);

                edges.add(new Edge(node1, node2, i));
            }
        }
    }

    public void paintComponent(Graphics g){


    }





}
