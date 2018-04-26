package assignment7;

import java.io.File;
import java.util.*;

public class cheaters {

    private static Thread[] ThreadList;
    private static Mapper[] MapList;
    private static int[][] plagiarismGrid;
    private static Map<Integer, Pair> treeMap;
    public static void main(String [] args){

        String fileName = args[0];
        int phraseSize = Integer.parseInt(args[1]);
        File files = new File(fileName);
        File[] list = files.listFiles();

        ThreadList = new Thread[list.length];
        MapList = new Mapper[list.length];
        plagiarismGrid = new int[list.length][list.length];
        treeMap = new TreeMap(Collections.reverseOrder());
        System.out.println("Creating Maps...");
        createMaps(list, phraseSize);
        System.out.println("Comparing Maps...");
        compareMaps();
        System.out.println("Getting Results...");
        getResults();
        System.out.println("Printing Results...");
        printOutResults();
    }

    private static void createMaps(File[] list, int size){
        for(int i = 0; i < list.length; i++){
            MapList[i] = new Mapper(list[i], size);
            ThreadList[i] = new Thread(MapList[i]);
            ThreadList[i].start();
        }
        for(Thread t : ThreadList){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void compareMaps(){
        for(int i = 0; i < MapList.length - 1; i++){
            for(int j = i + 1; j < MapList.length; j ++){
                Set<String> intersection = new HashSet<>(MapList[i].phrases);
                intersection.retainAll(MapList[j].phrases);
                plagiarismGrid[i][j] = intersection.size();
            }
        }
    }

    private static void getResults(){
        Map<Integer, Pair> unsortedMap = new HashMap<>();
        for(int i = 0; i < MapList.length - 1; i++){
            for(int j = i + 1; j < MapList.length; j++){
                if(plagiarismGrid[i][j] < 2){
                    continue;
                }
                unsortedMap.put(plagiarismGrid[i][j], new Pair(MapList[i], MapList[j]));
            }
        }
        treeMap.putAll(unsortedMap);
    }

    private static void printOutResults(){
        for(Map.Entry<Integer, Pair> entry : treeMap.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue().toString());
        }
    }
}
