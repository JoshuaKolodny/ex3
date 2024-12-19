package strategies;

import image_char_matching.SubImgCharMatcher;

import java.util.TreeMap;
import java.util.TreeSet;

/**
 * The RoundDownStrategy class implements the {@link RoundStrategy} interface
 * and provides a strategy to match brightness values to the nearest character
 * by rounding down to the closest lower brightness value.
 *
 * <p>This strategy selects the character associated with the brightness value
 * that is less than or equal to the given brightness, prioritizing lower values.</p>
 *
 * @author Joshua Kolodny, Itamar Lev Ari
 */
public class RoundDownStrategy implements RoundStrategy {

    /** The matcher containing brightness mappings and character sets. */
    private final SubImgCharMatcher subImgCharMatcher;

    /**
     * Constructs a new RoundDownStrategy with the specified matcher.
     *
     * @param subImgCharMatcher the matcher used to retrieve brightness mappings.
     */
    public RoundDownStrategy(SubImgCharMatcher subImgCharMatcher) {
        this.subImgCharMatcher = subImgCharMatcher;
    }

    /**
     * Retrieves the character whose brightness value is closest to the given brightness
     * by rounding down to the nearest lower brightness value.
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

        // Find the nearest lower key in the map
        double lowerKey = charBrightnessMap.floorKey(oldCharBrightness);

        return charBrightnessMap.get(lowerKey).first();
    }
}
