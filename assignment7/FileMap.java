/* Cheaters! <FileMap.java>
 * EE422C Project 7 submission by
 * Benson Huang
 * bkh642
 * Nimay Kumar
 * nrk472
 * Slip days used: <0>
 * Spring 2018
 */

package assignment7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static assignment7.cheaters.DocEncoding;
import static assignment7.cheaters.plagiarismGrid;


class FileMap implements Runnable {

    private static ConcurrentHashMap<String, List<File>> map = new ConcurrentHashMap<>();
    private File[] list;
    private int size;
    private int start;
    private int end;

    /**
     * Creates a new FileMap object
     * @param list array of files
     * @param start
     * @param end
     * @param size
     */
    public FileMap(File[] list, int start, int end, int size){

        this.start = start;
        this.end = Math.min(end, list.length);
        this.list = list;
        this.size = size;
    }

    @Override
    public void run() {
        for(int i = start; i < end; i++){
            ArrayList<String> convertedDoc = new ArrayList<>();
            try {
                Scanner inFile = new Scanner(new BufferedReader(new FileReader(list[i])));
                while(inFile.hasNextLine()){
                    String s = inFile.nextLine();
                    if(s.equals(""))
                        continue;
                    String[] words;
                    words = s.replaceAll("[^a-zA-Z0-9 ]", "").toLowerCase().split("\\s+");
                    convertedDoc.addAll(Arrays.asList(words));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            for(int pIndex = 0; pIndex < convertedDoc.size() - this.size; pIndex++){
                String phrase = convertedDoc.subList(pIndex, pIndex + this.size).toString();
                addPhrase(phrase, list[i]);
            }
        }
    }

    private static void addPhrase(String phrase, File f){

        if(map.containsKey(phrase)){

            if(map.get(phrase).contains(f)){
                return;
            }
            map.get(phrase).add(f);
        }
        else{
            List<File> fileSet = new ArrayList<>();
            fileSet.add(f);
            map.put(phrase, fileSet);
        }
    }

    public static void updateGrid(){

        for(HashMap.Entry<String, List<File>> entry : map.entrySet()){

            List<File> files = entry.getValue();
            for(int i = 0; i < files.size() - 1; i++){

                for(int j = i + 1; j < files.size(); j++){

                    plagiarismGrid[DocEncoding.get(files.get(i))][DocEncoding.get(files.get(j))]++;
                }
            }
        }
    }
}
