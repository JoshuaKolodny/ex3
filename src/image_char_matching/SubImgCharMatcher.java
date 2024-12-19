package image_char_matching;

import strategies.RoundAbsStrategy;
import strategies.RoundStrategy;
import factories.RoundStrategyFactory;

import java.util.TreeMap;
import java.util.TreeSet;

/**
 * The SubImgCharMatcher class manages the mapping between brightness values
 * and characters for sub-image matching in ASCII art generation.
 *
 * <p>This class allows adding and removing characters, setting rounding strategies,
 * and retrieving the best matching character for a given brightness value.</p>
 *
 * @author Joshua Kolodny, Itamar Lev Ari
 */
public class SubImgCharMatcher {

    /** A map linking brightness values to sets of characters. */
    private final TreeMap<Double, TreeSet<Character>> charBrightnessMap;

    /** Maximum brightness value among the characters. */
    private double maxBrightness;

    /** Minimum brightness value among the characters. */
    private double minBrightness;

    /** The strategy used to determine the nearest character for a brightness value. */
    private RoundStrategy roundStrategy;

    /**
     * Constructs a new SubImgCharMatcher with the given character set.
     *
     * @param charset an array of characters to initialize the matcher.
     */
    public SubImgCharMatcher(char[] charset) {
        charBrightnessMap = new TreeMap<>();
        for (char c : charset) {
            addChar(c);
        }
        maxBrightness = Double.MIN_VALUE;
        minBrightness = Double.MAX_VALUE;
        calculateMinMaxBrightness();
        this.roundStrategy = new RoundAbsStrategy(this);
    }

    /**
     * Retrieves the best matching character for the specified brightness value
     * using the current rounding strategy.
     *
     * @param brightness the brightness value to match.
     * @return the character that best matches the brightness value.
     */
    public char getCharByImageBrightness(double brightness) {
        return roundStrategy.getNearestCharBrightness(brightness);
    }

    /**
     * Calculates the brightness of a character based on its boolean representation.
     *
     * @param c the character whose brightness is to be calculated.
     * @return the brightness value of the character.
     */
    private double calculateCharBrightness(char c) {
        boolean[][] charBooleanArray = CharConverter.convertToBoolArray(c);
        int trueCounter = 0;
        for (int i = 0; i < charBooleanArray.length; i++) {
            for (int j = 0; j < charBooleanArray[0].length; j++) {
                if (charBooleanArray[i][j]) {
                    trueCounter++;
                }
            }
        }
        return (double) trueCounter / (charBooleanArray.length * charBooleanArray[0].length);
    }

    /**
     * Adds a character to the matcher and updates the brightness map.
     *
     * @param c the character to add.
     */
    public void addChar(char c) {
        double charBrightness = calculateCharBrightness(c);
        charBrightnessMap.computeIfAbsent(charBrightness, k -> new TreeSet<>()).add(c);
        if (charBrightness < minBrightness) {
            minBrightness = charBrightness;
        }
        if (charBrightness > maxBrightness) {
            maxBrightness = charBrightness;
        }
    }

    /**
     * Removes a character from the matcher and updates the brightness map.
     *
     * @param c the character to remove.
     */
    public void removeChar(char c) {
        double charBrightness = calculateCharBrightness(c);
        if (charBrightnessMap.containsKey(charBrightness)) {
            charBrightnessMap.get(charBrightness).remove(c);
            if (charBrightnessMap.get(charBrightness).isEmpty()) {
                charBrightnessMap.remove(charBrightness);
            }
        }
        if (!charBrightnessMap.containsKey(minBrightness) || !charBrightnessMap.containsKey(maxBrightness)) {
            calculateMinMaxBrightness();
        }
    }

    /**
     * Recalculates the minimum and maximum brightness values in the map.
     */
    private void calculateMinMaxBrightness() {
        if (charBrightnessMap.isEmpty()) {
            minBrightness = Double.MAX_VALUE;
            maxBrightness = Double.MIN_VALUE;
            return;
        }
        this.minBrightness = charBrightnessMap.firstKey();
        this.maxBrightness = charBrightnessMap.lastKey();
    }

    /**
     * Gets the minimum brightness value in the matcher.
     *
     * @return the minimum brightness value.
     */
    public double getMinBrightness() {
        return minBrightness;
    }

    /**
     * Gets the maximum brightness value in the matcher.
     *
     * @return the maximum brightness value.
     */
    public double getMaxBrightness() {
        return maxBrightness;
    }

    /**
     * Sets the rounding strategy for matching brightness values to characters.
     *
     * @param roundStrategy the name of the rounding strategy to set.
     * @throws IllegalArgumentException if the rounding strategy is invalid.
     */
    public void setRoundStrategy(String roundStrategy) throws IllegalArgumentException {
        RoundStrategyFactory roundStrategyFactory = new RoundStrategyFactory(this);
        this.roundStrategy = roundStrategyFactory.buildRoundStrategy(roundStrategy);
    }

    /**
     * Retrieves the character brightness map.
     *
     * @return a map linking brightness values to sets of characters.
     */
    public TreeMap<Double, TreeSet<Character>> getCharBrightnessMap() {
        return charBrightnessMap;
    }
}
