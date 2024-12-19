package strategies;

import image_char_matching.SubImgCharMatcher;

import java.util.TreeMap;
import java.util.TreeSet;

/**
 * The RoundAbsStrategy class implements the {@link RoundStrategy} interface
 * and provides a strategy to match brightness values to the nearest character
 * using absolute distance calculations.
 *
 * <p>This strategy ensures that the character selected has a brightness value
 * closest to the given brightness, considering both lower and higher nearest keys.</p>
 *
 * @author Joshua Kolodny, Itamar Lev Ari
 */
public class RoundAbsStrategy implements RoundStrategy {

    /** The matcher containing brightness mappings and character sets. */
    private final SubImgCharMatcher subImgCharMatcher;

    /**
     * Constructs a new RoundAbsStrategy with the specified matcher.
     *
     * @param subImgCharMatcher the matcher used to retrieve brightness mappings.
     */
    public RoundAbsStrategy(SubImgCharMatcher subImgCharMatcher) {
        this.subImgCharMatcher = subImgCharMatcher;
    }

    /**
     * Retrieves the character whose brightness value is closest to the given brightness
     * using the absolute distance calculation.
     *
     * @param brightness the brightness value to match.
     * @return the character that best matches the brightness value.
     */
    @Override
    public char getNearestCharBrightness(double brightness) {
        double minBrightness = subImgCharMatcher.getMinBrightness();
        double maxBrightness = subImgCharMatcher.getMaxBrightness();
        TreeMap<Double, TreeSet<Character>> charBrightnessMap = subImgCharMatcher.getCharBrightnessMap();

        // Calculate the normalized brightness value
        double oldCharBrightness = (maxBrightness - minBrightness) * brightness + minBrightness;

        // Check if exact brightness exists in the map
        if (charBrightnessMap.containsKey(oldCharBrightness)) {
            return charBrightnessMap.get(oldCharBrightness).first();
        }

        // Find the nearest keys in the map
        Double lowerKey = charBrightnessMap.floorKey(oldCharBrightness);
        Double higherKey = charBrightnessMap.ceilingKey(oldCharBrightness);

        // Handle edge cases where one of the keys might be null
        if (lowerKey == null) return charBrightnessMap.get(higherKey).first();
        if (higherKey == null) return charBrightnessMap.get(lowerKey).first();

        // Calculate distances to the nearest keys
        double distanceToLower = Math.abs(lowerKey - oldCharBrightness);
        double distanceToHigher = Math.abs(higherKey - oldCharBrightness);

        // Determine the nearest key
        double nearestKey = (distanceToLower <= distanceToHigher) ? lowerKey : higherKey;

        return charBrightnessMap.get(nearestKey).first();
    }
}
