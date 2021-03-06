/* Cheaters! <cheaters.java>
 * EE422C Project 7 submission by
 * Benson Huang
 * bkh642
 * Nimay Kumar
 * nrk472
 * Slip days used: <0>
 * Spring 2018
 */

package assignment7;

import java.io.File;
import java.util.*;

public class cheaters {

    private static Thread[] ThreadList;
    private static FileMap[] MapList;
    protected static int[][] plagiarismGrid;
    protected static Hashtable<File, Integer> DocEncoding;
    protected static Map<Integer, Pair> treeMap;

    private static int len;
    private static File[] list;
    private static final int THREADCOUNT = 5;

    public static void main(String [] args){

        String fileName = args[0];
        int phraseSize = Integer.parseInt(args[1]);
        int threshHold = Integer.parseInt(args[2]);

        File files = new File(fileName);
        list = files.listFiles();

        len = (int) Math.ceil(1.0 * list.length/THREADCOUNT);
        ThreadList = new Thread[THREADCOUNT];
        MapList = new FileMap[THREADCOUNT];
        plagiarismGrid = new int[list.length][list.length];
        DocEncoding = new Hashtable<>();
        treeMap = new TreeMap(Collections.reverseOrder());

        long startTime = System.nanoTime();
        createDictionary();
        createMap(phraseSize);
        FileMap.updateGrid();
        getResults(threshHold);

        long endTime = System.nanoTime();
        double duration = (double)(endTime - startTime) /1000000000.0;
        System.out.println(THREADCOUNT + " threads: " + duration + " seconds");
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

    /**
     * Creates dictionary
     */
    private static void createDictionary(){

        for(int i = 0; i < list.length; i++ ){

            DocEncoding.put(list[i], i);
        }
    }

    /**
     * Creates and starts THREADCOUNT new threads running FileMaps
     * @param size Filemap size
     */
    private static void createMap(int size){

        for(int i = 0; i < THREADCOUNT; i++){

            MapList[i] = new FileMap(list, i*len, (i+1)*len, size);
            ThreadList[i] = new Thread(MapList[i]);
            ThreadList[i].start();
        }
        for(Thread t : ThreadList){

            try {
                t.join();
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Puts all new collisions into treeMap
     * @param threshHold minimum number of similarities
     */
    private static void getResults(int threshHold){

        Map<Integer, Pair> unsortedMap = new HashMap<>();

        for(int i = 0; i < list.length - 1; i++){

            for(int j = i + 1; j < list.length; j++){

                int collisions = plagiarismGrid[DocEncoding.get(list[i])][DocEncoding.get(list[j])] + plagiarismGrid[DocEncoding.get(list[j])][DocEncoding.get(list[i])];

                if(collisions <= threshHold){

                    continue;
                }

                unsortedMap.put(collisions, new Pair(list[i], list[j]));
            }
        }
        treeMap.putAll(unsortedMap);
    }

    /**
     * Prints out results
     */
    private static void printOutResults(){

        for(Map.Entry<Integer, Pair> entry : treeMap.entrySet()){

            System.out.println(entry.getKey() + ": " + entry.getValue().get1().toString() + ", " + entry.getValue().get2().toString());
        }
    }
}
