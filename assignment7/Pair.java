package assignment7;

public class Pair {

    public Mapper map1;
    public Mapper map2;

    public Pair(Mapper map1, Mapper map2){
        this.map1 = map1;
        this.map2 = map2;
    }

    @Override
    public String toString() {
        return (map1.name + " " + map2.name);
    }

}
