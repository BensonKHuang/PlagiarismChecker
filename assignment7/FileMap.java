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
    private File file;
    private int size;

    public FileMap(File file, int size){

        this.file = file;
        this.size = size;
    }

    @Override
    public void run() {
        ArrayList<String> convertedDoc = new ArrayList<>();
        try {
            Scanner inFile = new Scanner(new BufferedReader(new FileReader(file)));
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

        for(int i = 0; i < convertedDoc.size() - this.size; i++){
            String phrase = convertedDoc.subList(i, i + this.size).toString();
            addPhrase(phrase, file);
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
