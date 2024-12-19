package strategies;

/**
 * The RoundStrategy interface defines the contract for implementing
 * rounding strategies in the ASCII art generation process.
 *
 * <p>A rounding strategy determines the nearest character for a given
 * brightness value based on specific rules (e.g., rounding up, down, or absolute).</p>
 *
 * @author Joshua Kolodny, Itamar Lev Ari
 */
public interface RoundStrategy {

    /**
     * Retrieves the character whose brightness value is closest to the given brightness.
     *
     * @param brightness the brightness value to match.
     * @return the nearest character based on the implemented strategy.
     */
    char getNearestCharBrightness(double brightness);
}
