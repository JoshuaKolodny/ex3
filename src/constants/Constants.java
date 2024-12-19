package constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * The Constants class contains constant values and utility methods
 * used throughout the ASCII art application. These constants include
 * default configurations, command strings, and various parameters
 * needed for processing and validation.
 *
 * <p>This class is designed to centralize all constant values to improve
 * maintainability and readability of the codebase.</p>
 *
 * @author Joshua Kolodny, Itamar Lev Ari
 */
public class Constants {

    /** Default resolution for ASCII art generation. */
    public static final int DEFAULT_RESOLUTION = 2;

    /** Default character set for ASCII art generation. */
    public static final TreeSet<Character> DEFAULT_CHARSET = new TreeSet<>(Arrays.asList('0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9'));

    /** Command to display the current character set. */
    public static final String CHAR_INPUT = "chars";

    /** Command argument to indicate all characters. */
    public static final String ALL_ARG = "all";

    /** Minimum ASCII value for valid characters. */
    public static final char MIN_ASCII_VAL = 32;

    /** Maximum ASCII value for valid characters. */
    public static final char MAX_ASCII_VAL = 126;

    /** Command for changing resolution. */
    public static final String RES_INPUT = "res ";

    /** Error message for invalid image paths. */
    public static final String INVALID_IMAGE_PATH = "Did not execute due to a problem with the image path";

    /** Command for changing rounding strategies. */
    public static final String ROUND_INPUT = "round ";

    /** Command for changing output method. */
    public static final String OUTPUT_INPUT = "output ";

    /** Font used in HTML output files. */
    public static final String HTML_FONT = "Courier New";

    /** Default name for the output HTML file. */
    public static final String OUTPUT_FILE_NAME = "out.html";

    /** Command description for changing output method. */
    public static final String OUTPUT_COMMAND = "change output method";

    /** Command for generating ASCII art. */
    public static final String ASCII_ART_INPUT = "asciiArt ";

    /** Command argument for space character. */
    public static final String SPACE_ARG = "space";

    /** Multiplier for red channel in brightness calculation. */
    public static final double RED_MULT = 0.2126;

    /** Multiplier for green channel in brightness calculation. */
    public static final double GREEN_MULT = 0.7152;

    /** Multiplier for blue channel in brightness calculation. */
    public static final double BLUE_MULT = 0.0722;

    /** Maximum RGB value for colors. */
    public static final int MAX_RGB_VAL = 255;

    /** Command description for changing resolution. */
    public static final String CHANGE_RESOLUTION = "change resolution";

    /**
     * Generates an error message for invalid command format.
     *
     * @param command the command that was incorrectly formatted.
     * @return a formatted error message indicating the issue.
     */
    public static String incorrectFormatMessage(String command) {
        return "Did not " + command + " due to incorrect format.";
    }

    /** Error message for incorrect commands. */
    public static final String INCORRECT_COMMAND = "Did not execute due to incorrect command.";

    /** Error message for resolution boundary violations. */
    public static final String BOUNDARIES_COMMAND = "Did not change resolution due to exceeding boundaries";

    /** Command description for changing rounding method. */
    public static final String ROUND_COMMAND = "change rounding method";

    /** Prompt for user input. */
    public static final String ENTER_MESSAGE = ">>> ";

    /** Command to exit the application. */
    public static final String EXIT_INPUT = "exit";

    /** Command for adding characters to the character set. */
    public static final String ADD_INPUT = "add ";

    /** Command for removing characters from the character set. */
    public static final String REMOVE_INPUT = "remove ";

    /** Message displayed when resolution is successfully changed. */
    public static final String NEW_RES_MESSAGE = "Resolution set to ";

    /** Argument to increase resolution. */
    public static final String RES_UP = "up";

    /** Argument to decrease resolution. */
    public static final String RES_DOWN = "down";
}
