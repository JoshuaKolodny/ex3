package factories;

import constants.Constants;
import image_char_matching.SubImgCharMatcher;
import strategies.RoundAbsStrategy;
import strategies.RoundDownStrategy;
import strategies.RoundStrategy;
import strategies.RoundUpStrategy;

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
                throw new IllegalArgumentException(Constants.incorrectFormatMessage(Constants.ROUND_COMMAND));
        }
    }
}
