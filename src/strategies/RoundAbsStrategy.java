package strategies;

import image_char_matching.SubImgCharMatcher;

import java.util.TreeMap;
import java.util.TreeSet;

public class RoundAbsStrategy implements RoundStrategy {
    private final SubImgCharMatcher subImgCharMatcher;

    public RoundAbsStrategy(SubImgCharMatcher subImgCharMatcher) {
        this.subImgCharMatcher = subImgCharMatcher;
    }

    @Override
    public char getNearestCharBrightness(double brightness) {
        double minBrightness = subImgCharMatcher.getMinBrightness();
        double maxBrightness = subImgCharMatcher.getMaxBrightness();
        TreeMap<Double, TreeSet<Character>> charBrightnessMap = subImgCharMatcher.getCharBrightnessMap();
        double oldCharBrightness = (maxBrightness - minBrightness) * brightness + minBrightness;
        if (charBrightnessMap.containsKey(oldCharBrightness)) {
            return charBrightnessMap.get(oldCharBrightness).first();
        }
        // checking for absolute value of nearest key
        // Find the nearest keys
        double lowerKey = charBrightnessMap.floorKey(oldCharBrightness); // Closest key <= oldCharBrightness
        double higherKey = charBrightnessMap.ceilingKey(oldCharBrightness); // Closest key >= oldCharBrightness

        double distanceToLower = Math.abs(lowerKey - oldCharBrightness);
        double distanceToHigher = Math.abs(higherKey - oldCharBrightness);
        double nearestKey = (distanceToLower <= distanceToHigher) ? lowerKey : higherKey;

        return charBrightnessMap.get(nearestKey).first();
    }
}
