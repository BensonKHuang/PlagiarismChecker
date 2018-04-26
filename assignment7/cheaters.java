package assignment7;

import java.io.File;
import java.util.*;

public class cheaters {

    private static Thread[] ThreadList;
    private static FileMap[] MapList;
    protected static Integer[][] plagiarismGrid;
    protected static Hashtable<File, Integer> DocEncoding;
    private static Map<Integer, Pair> treeMap;
    private static File[] list;

    public static void main(String [] args){

        String fileName = args[0];
        int phraseSize = Integer.parseInt(args[1]);
        File files = new File(fileName);
        list = files.listFiles();

        ThreadList = new Thread[list.length];
        MapList = new FileMap[list.length];
        plagiarismGrid = new Integer[list.length][list.length];
        DocEncoding = new Hashtable<>();
        treeMap = new TreeMap(Collections.reverseOrder());

        initializeGrid();
        createDictionary();
        createMap(phraseSize);
        getResults();
        printOutResults();
    }

    private static void initializeGrid(){
        int size = plagiarismGrid.length;
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                plagiarismGrid[i][j] = 0;
            }
        }
    }
    private static void createDictionary(){
        for(int i = 0; i < list.length; i++ ){
            DocEncoding.put(list[i], i);
        }
    }

    private static void createMap(int size){
        for(int i = 0; i < list.length; i++){
            MapList[i] = new FileMap(list[i], size);
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

    private static void getResults(){
        Map<Integer, Pair> unsortedMap = new HashMap<>();
        for(int i = 0; i < list.length - 1; i++){
            for(int j = i + 1; j < list.length; j++){
                int collisions = plagiarismGrid[DocEncoding.get(list[i])][DocEncoding.get(list[j])] + plagiarismGrid[DocEncoding.get(list[j])][DocEncoding.get(list[i])];

                if(collisions < 100){
                    continue;
                }
                unsortedMap.put(collisions, new Pair(list[i], list[j]));
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
