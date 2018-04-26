package assignment7;

import java.io.File;

public class Pair {

    public File f1;
    public File f2;

    public Pair(File f1, File f2){
        this.f1 = f1;
        this.f2 = f2;
    }

    @Override
    public String toString() {
        return (f1.getName().replaceAll(".txt", "") + " " + f2.getName().replaceAll(".txt", ""));
    }

}
