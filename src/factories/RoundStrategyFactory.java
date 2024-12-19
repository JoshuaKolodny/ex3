package factories;

import constants.Constants;
import image_char_matching.SubImgCharMatcher;
import strategies.RoundAbsStrategy;
import strategies.RoundDownStrategy;
import strategies.RoundStrategy;
import strategies.RoundUpStrategy;

/**
 * The RoundStrategyFactory class is responsible for creating instances of
 * {@link RoundStrategy} based on the specified rounding parameter.
 *
 * <p>This factory supports multiple rounding strategies, such as rounding up,
 * rounding down, and rounding to the absolute value. If an unsupported
 * rounding parameter is provided, an {@link IllegalArgumentException} is thrown
 * with an appropriate error message.</p>
 *
 * @author Joshua Kolodny, Itamar Lev Ari
 */
public class RoundStrategyFactory {

    /** Matcher used for associating brightness values with characters. */
    private final SubImgCharMatcher subImgCharMatcher;

    /**
     * Constructs a new RoundStrategyFactory with the specified matcher.
     *
     * @param subImgCharMatcher the matcher used to associate brightness values with characters.
     */
    public RoundStrategyFactory(SubImgCharMatcher subImgCharMatcher) {
        this.subImgCharMatcher = subImgCharMatcher;
    }

    /**
     * Builds a {@link RoundStrategy} instance based on the provided rounding parameter.
     *
     * @param roundParam the rounding strategy parameter (e.g., "up", "down", or "abs").
     * @return a {@link RoundStrategy} instance for the specified rounding parameter.
     * @throws IllegalArgumentException if the rounding parameter is not supported.
     */
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
