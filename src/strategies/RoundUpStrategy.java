package strategies;

import image_char_matching.SubImgCharMatcher;

import java.util.TreeMap;
import java.util.TreeSet;

public class RoundUpStrategy implements RoundStrategy {
    private final SubImgCharMatcher subImgCharMatcher;

    public RoundUpStrategy(SubImgCharMatcher subImgCharMatcher) {
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

        double higherKey = charBrightnessMap.ceilingKey(oldCharBrightness);
        return charBrightnessMap.get(higherKey).first();
    }
}
