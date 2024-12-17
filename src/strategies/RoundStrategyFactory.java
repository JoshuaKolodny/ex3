package strategies;

import constants.Constants;
import image_char_matching.SubImgCharMatcher;

public class RoundStrategyFactory {
    private final SubImgCharMatcher subImgCharMatcher;

    public RoundStrategyFactory(SubImgCharMatcher subImgCharMatcher) {
        this.subImgCharMatcher = subImgCharMatcher;
    }

    public RoundStrategy buildRoundStrategy(String roundParam) throws IllegalArgumentException {
        switch (roundParam) {
            case "up":
                return new RoundUpStrategy(subImgCharMatcher);
            case "down":
                return new RoundDownStrategy(subImgCharMatcher);
            case "abs":
                return new RoundAbsStrategy(subImgCharMatcher);
            default:
                throw new IllegalArgumentException(Constants.stringIncorrectFormatMessage(Constants.ROUND_COMMAND));
        }
    }
}
