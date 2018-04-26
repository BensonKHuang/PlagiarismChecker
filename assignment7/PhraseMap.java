package assignment7;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class PhraseMap {

    private String phrase;
    public File file;
    private static HashMap<String, HashSet<File>> map = new HashMap<>();

    public PhraseMap(String name, File f){

        phrase = name;
        this.file = f;
        
        if(map.containsKey(name)){
            map.get(name).add(f);
        }
        else{
            map.put(name, new HashSet<File>());
            map.get(name).add(f);
        }
    }

    public static HashMap<String, HashSet<File>> getMap() {

        return map;
    }

    public String getPhrase(){

        return phrase;
    }

}
