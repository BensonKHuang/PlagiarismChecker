package assignment7;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class cheaters {

    private static Thread[] ThreadList;
    private static Mapper[] MapList;

    public static void main(String [] args){

        String fileName = args[0];
        int phraseSize = Integer.parseInt(args[1]);
        File files = new File(fileName);
        File[] list = files.listFiles();

        ThreadList = new Thread[list.length];
        MapList = new Mapper[list.length];
        createMaps(list, phraseSize);

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
        for(Mapper m : MapList){
            System.out.println(m.name + " " + m.map.size());
        }
        //System.out.println(MapList[0].map.toString());

    }
}
