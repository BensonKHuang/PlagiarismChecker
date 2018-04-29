package assignment7;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Graph extends Application {

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

        for(Integer i : map.keySet()){

            if(i >= threshold){

                Pair p = map.get(i);
                Node node1 = new Node(map.get(i).get1().toString());
                Node node2 = new Node(map.get(i).get2().toString());

                nodes.add(node1);
                nodes.add(node2);

                edges.add(new Edge(node1, node2, i));
            }
        }
    }


    @Override
    public void start(Stage primaryStage) {

    }


}
