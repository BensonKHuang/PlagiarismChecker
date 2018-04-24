package assignment7;

import java.io.File;
import java.io.FileNotFoundException;
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
            Scanner inFile = new Scanner(file);
            while(inFile.hasNextLine()){
                String s = inFile.nextLine();
                s = s.replaceAll("^a-zA-Z0=9", " ").toLowerCase();
                String[] words = s.split("\\s+");
                convertedDoc.addAll(Arrays.asList(words));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for(int i = 0; i < convertedDoc.size() - size; i++){
            String phrase = (String) convertedDoc.subList(i, i + size).toString();
            Integer n = map.get(phrase);
            n = (n == null) ? 1 : (n + 1);
            map.put(phrase, n);
        }
        System.out.println("Finished");
    }
}
