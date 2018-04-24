package assignment7;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

class Mapper implements Runnable {

    public Map<String, Integer> map;
    public File file;
    public String name;
    private int size;

    public Mapper(File file, int size){
        this.map = new HashMap<>();
        this.file = file;
        this.name = file.getName().replaceAll(".txt", "");
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
            Integer n = map.get(phrase);
            n = (n == null) ? 1 : (n + 1);
            map.put(phrase, n);
        }
        //System.out.println("Finished");
    }
}
