package strategies;

import image_char_matching.SubImgCharMatcher;

import java.util.TreeMap;
import java.util.TreeSet;

public class RoundDownStrategy implements RoundStrategy {
    private final SubImgCharMatcher subImgCharMatcher;

    public RoundDownStrategy(SubImgCharMatcher subImgCharMatcher) {
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

        double lowerKey = charBrightnessMap.floorKey(oldCharBrightness);
        return charBrightnessMap.get(lowerKey).first();
    }
}
