package ascii_art;

import java.util.HashSet;

public class AsciiArtSingleton {
    private static final AsciiArtSingleton instance = new AsciiArtSingleton();

    // Previous run properties
    private int prevResolution;
    private double[][] prevSubImagesBrightnesses;


    private char[][] prevImage;  // Holds the previous ASCII image
    private HashSet<Character> removedCharsSet;
    private HashSet<Character> addedCharsSet;

    private AsciiArtSingleton() {
        removedCharsSet = new HashSet<>();
        addedCharsSet = new HashSet<>();
    }

    public static AsciiArtSingleton getInstance() {
        return instance;
    }

    public boolean isSameCharset() {
        return addedCharsSet.isEmpty() && removedCharsSet.isEmpty();
    }

    public void removeFromPrevCharset(char c) {
        if (!addedCharsSet.remove(c)) {
            removedCharsSet.add(c);
        }
    }

    public void addToPrevCharset(char c) {
        if (!removedCharsSet.remove(c)) {
            addedCharsSet.add(c);
        }
    }

    // Resolution methods
    public int getPrevResolution() {
        return prevResolution;
    }

    public void setPrevResolution(int resolution) {
        this.prevResolution = resolution;
    }

    // Sub-image brightness methods
    public double[][] getPrevSubImagesBrightnesses() {
        return prevSubImagesBrightnesses;
    }

    public void setPrevSubImagesBrightnesses(double[][] brightnesses) {
        this.prevSubImagesBrightnesses = brightnesses;
    }

    // Previous ASCII image methods
    public char[][] getPrevImage() {
        return prevImage;
    }

    public void setPrevImage(char[][] image) {
        this.prevImage = image;
    }

    public void resetCharset() {
        addedCharsSet = new HashSet<>();
        removedCharsSet = new HashSet<>();
    }
}
