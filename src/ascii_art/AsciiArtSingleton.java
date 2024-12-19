package ascii_art;

import java.util.HashSet;

/**
 * Singleton class that manages state for the ASCII art generation process.
 * It tracks the previous resolution, sub-image brightness values, and
 * the previously generated ASCII art. It also maintains a record of
 * added and removed characters for the charset.
 *
 * @author Joshua Kolodny, Itamar Lev Ari
 */
public class AsciiArtSingleton {

    /** Singleton instance of AsciiArtSingleton. */
    private static final AsciiArtSingleton instance = new AsciiArtSingleton();

    /** Previous resolution used in ASCII art generation. */
    private int prevResolution;

    /** Brightness values of sub-images from the previous run. */
    private double[][] prevSubImagesBrightnesses;

    /** The previously generated ASCII art image. */
    private char[][] prevImage;

    /** Set of characters removed from the charset. */
    private HashSet<Character> removedCharsSet;

    /** Set of characters added to the charset. */
    private HashSet<Character> addedCharsSet;

    /** Private constructor to enforce singleton pattern. */
    private AsciiArtSingleton() {
        removedCharsSet = new HashSet<>();
        addedCharsSet = new HashSet<>();
    }

    /**
     * Returns the singleton instance of AsciiArtSingleton.
     *
     * @return the singleton instance.
     */
    public static AsciiArtSingleton getInstance() {
        return instance;
    }

    /**
     * Checks if the character set has remained unchanged.
     *
     * @return true if the charset is the same, false otherwise.
     */
    public boolean isSameCharset() {
        return addedCharsSet.isEmpty() && removedCharsSet.isEmpty();
    }

    /**
     * Removes a character from the charset of the previous run.
     * If the character was newly added, it removes it from the added set.
     *
     * @param c the character to remove.
     */
    public void removeFromPrevCharset(char c) {
        if (!addedCharsSet.remove(c)) {
            removedCharsSet.add(c);
        }
    }

    /**
     * Adds a character to the charset of the previous run.
     * If the character was previously removed, it removes it from the removed set.
     *
     * @param c the character to add.
     */
    public void addToPrevCharset(char c) {
        if (!removedCharsSet.remove(c)) {
            addedCharsSet.add(c);
        }
    }

    /**
     * Gets the resolution used in the previous ASCII art generation.
     *
     * @return the previous resolution.
     */
    public int getPrevResolution() {
        return prevResolution;
    }

    /**
     * Sets the resolution for the next ASCII art generation.
     *
     * @param resolution the new resolution.
     */
    public void setPrevResolution(int resolution) {
        this.prevResolution = resolution;
    }

    /**
     * Gets the brightness values of the sub-images from the previous run.
     *
     * @return a 2D array of brightness values.
     */
    public double[][] getPrevSubImagesBrightnesses() {
        return prevSubImagesBrightnesses;
    }

    /**
     * Sets the brightness values of the sub-images for the next run.
     *
     * @param brightnesses the new brightness values.
     */
    public void setPrevSubImagesBrightnesses(double[][] brightnesses) {
        this.prevSubImagesBrightnesses = brightnesses;
    }

    /**
     * Gets the previously generated ASCII art image.
     *
     * @return a 2D character array representing the ASCII art.
     */
    public char[][] getPrevImage() {
        return prevImage;
    }

    /**
     * Sets the ASCII art image for the next run.
     *
     * @param image the new ASCII art image.
     */
    public void setPrevImage(char[][] image) {
        this.prevImage = image;
    }

    /**
     * Resets the added and removed character sets.
     */
    public void resetCharset() {
        addedCharsSet = new HashSet<>();
        removedCharsSet = new HashSet<>();
    }
}
