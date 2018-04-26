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

    private static ConcurrentHashMap<String, HashSet<File>> map = new ConcurrentHashMap<>();
    private File[] list;
    private int size;
    private int start;
    private int end;

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
            updateGrid(phrase, f);
            map.get(phrase).add(f);
        }
        else{
            map.put(phrase, new HashSet<File>());
            map.get(phrase).add(f);
        }
    }

    private static void updateGrid(String phrase, File f){
        for(File found : map.get(phrase)){
            synchronized (plagiarismGrid[DocEncoding.get(f)][DocEncoding.get(found)]){
                plagiarismGrid[DocEncoding.get(f)][DocEncoding.get(found)]++;
            }
        }
    }
}
