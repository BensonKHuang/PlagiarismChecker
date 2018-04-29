/* Cheaters! <Pair.java>
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

/**
 * Helper Data structure to hold two files that share a common phrase
 */
public class Pair {

    public File f1;
    public File f2;

    /**
     * Creates a new pair containing f1, f2
     * @param f1 file 1
     * @param f2 file 2
     */
    public Pair(File f1, File f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    /**
     * Returns String representation of Pair object
     * @return
     */
    @Override
    public String toString() {
        return (f1.getName().replaceAll(".txt", "") + " " + f2.getName().replaceAll(".txt", ""));
    }

    /**
     *
     * @return String representing file 1
     */
    public String get1() {

        return f1.getName().replaceAll(".txt", "");
    }

    /**
     *
     * @return String representing file 2
     */
    public String get2() {

        return f2.getName().replaceAll(".txt", "");
    }
}
