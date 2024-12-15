package image_char_matching;

import java.util.TreeMap;
import java.util.TreeSet;

public class SubImgCharMatcher {
    private final char[] charset;
    private TreeMap<Double, TreeSet<Character>> charBrightnessMap;
    private double maxBrightness;
    private double minBrightness;

    public SubImgCharMatcher(char[] charset) {
        this.charset = charset;
    }

    public char getCharByImageBrightness(double brightness) {
        return 'a';
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
    }

    public void removeChar(char c) {
        double charBrightness = calculateCharBrightness(c);
        if (charBrightnessMap.containsKey(charBrightness)) {
            charBrightnessMap.get(charBrightness).remove(c);
            if (charBrightnessMap.get(charBrightness).isEmpty()) {
                charBrightnessMap.remove(charBrightness);
            }
        }
    }
}
