package constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeSet;

public class Constants {
    public static final int DEFAULT_RESOLUTION = 2;
    public static final TreeSet<Character> DEFAULT_CHARSET = new TreeSet<>(Arrays.asList('0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9'));
    public static final String CHAR_INPUT = "chars";
    public static final String ALL_ARG = "all";
    public static final char MIN_ASCII_VAL = 32;
    public static final char MAX_ASCII_VAL = 126;
    public static final String RES_INPUT = "res ";
    public static final String INVALID_IMAGE_PATH = "Did not execute due to a problem with the image path";
    public static final String ROUND_INPUT = "round ";
    public static final String OUTPUT_INPUT = "output ";
    public static final String HTML_FONT = "Courier New";
    public static final String OUTPUT_FILE_NAME = "out.html";
    public static final String OUTPUT_COMMAND = "change output method";
    public static final String ASCII_ART_INPUT = "asciiArt ";
    public static final String SPACE_ARG = "space";
    public static final double RED_MULT = 0.2126;
    public static final double GREEN_MULT = 0.7152;
    public static final double BLUE_MULT = 0.0722;
    public static final int MAX_RGB_VAL = 255;
    public static final String CHANGE_RESOLUTION = "change resolution";

    /**
     * function to handle repeated invalid messages.
     * @param command the command to add to the message.
     * @return String of the invalid message.
     */
    public static String incorrectFormatMessage(String command) {
        return "Did not " + command + " due to incorrect format.";
    }

    public static final String INCORRECT_COMMAND = "Did not execute due to incorrect command.";
    public static final String BOUNDARIES_COMMAND = "Did not change resolution due to exceeding boundaries";
    public static final String ROUND_COMMAND = "change rounding method";
    public static final String ENTER_MESSAGE = ">>> ";
    public static final String EXIT_INPUT = "exit";
    public static final String ADD_INPUT = "add ";
    public static final String REMOVE_INPUT = "remove ";
    public static final String NEW_RES_MESSAGE = "Resolution set to ";
    public static final String RES_UP = "up";
    public static final String RES_DOWN = "down";
}
