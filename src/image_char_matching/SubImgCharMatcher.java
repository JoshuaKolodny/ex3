package image_char_matching;

import strategies.RoundAbsStrategy;
import strategies.RoundStrategy;
import factories.RoundStrategyFactory;

import java.util.TreeMap;
import java.util.TreeSet;

public class SubImgCharMatcher {
    private final TreeMap<Double, TreeSet<Character>> charBrightnessMap;
    private double maxBrightness;
    private double minBrightness;

    private RoundStrategy roundStrategy;

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

    public char getCharByImageBrightness(double brightness) {
        return roundStrategy.getNearestCharBrightness(brightness);
    }

    private double calculateCharBrightness(char c) {
        // calculate brightness
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

    public void addChar(char c) {
        double charBrightness = calculateCharBrightness(c);
        if (charBrightnessMap.containsKey(charBrightness)) {
            charBrightnessMap.get(charBrightness).add(c);
        } else {
            TreeSet<Character> charSet = new TreeSet<>();
            charSet.add(c);
            charBrightnessMap.put(charBrightness, charSet);
        }
        // Update min and max brightness
        if (charBrightness < minBrightness) {
            minBrightness = charBrightness;
        }
        if (charBrightness > maxBrightness) {
            maxBrightness = charBrightness;
        }

    }

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

    private void calculateMinMaxBrightness() {
        if (charBrightnessMap.isEmpty()) {
            minBrightness = Double.MAX_VALUE;
            maxBrightness = Double.MIN_VALUE;
            return;
        }
        this.minBrightness = charBrightnessMap.firstKey();
        this.maxBrightness = charBrightnessMap.lastKey();
    }

    public double getMinBrightness() {
        return minBrightness;
    }

    public double getMaxBrightness() {
        return maxBrightness;
    }

    public void setRoundStrategy(String roundStrategy) throws IllegalArgumentException{
        RoundStrategyFactory roundStrategyFactory = new RoundStrategyFactory(this);
        this.roundStrategy = roundStrategyFactory.buildRoundStrategy(roundStrategy);
    }

    public TreeMap<Double, TreeSet<Character>> getCharBrightnessMap() {
        return charBrightnessMap;
    }


}
