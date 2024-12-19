package ascii_art;

import ascii_output.AsciiOutput;
import factories.AsciiOutputFactory;
import ascii_output.ConsoleAsciiOutput;
import constants.Constants;

import java.lang.IllegalArgumentException;

import exceptions.BoundariesResolutionException;
import image.Image;
import image.ImageEditor;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;
import java.util.TreeSet;

/**
 * The Shell class serves as the command-line interface for generating ASCII art.
 * It provides a variety of commands for managing the character set, resolution,
 * and output strategy for ASCII art generation. The class interacts with the
 * AsciiArtAlgorithm and other components to generate and display ASCII art.
 *
 * <p>Commands include adding/removing characters from the character set,
 * adjusting resolution, and selecting output methods. The class ensures
 * proper error handling and user input validation.</p>
 *
 * @author Joshua Kolodny, Itamar Lev Ari
 */
public class Shell {

    /** The set of characters used for ASCII art generation. */
    private final TreeSet<Character> charset;

    /** Matcher for associating brightness levels with characters. */
    private final SubImgCharMatcher subImgCharMatcher;

    /** The output method for ASCII art (e.g., console or file). */
    private AsciiOutput asciiOutput;

    /** The resolution of the ASCII art. */
    private int resolution;

    /** The input image to process. */
    private Image image;

    /** Minimum characters per row based on the image aspect ratio. */
    private int minCharsInRow;

    /** Maximum characters per row based on the image width. */
    private int maxCharsInRow;

    /** Singleton instance for managing shared state. */
    private final AsciiArtSingleton singleton;

    /**
     * Constructs a new Shell instance with default settings.
     * Initializes the character set, resolution, and output strategy.
     */
    public Shell() {
        this.charset = Constants.DEFAULT_CHARSET;
        this.resolution = Constants.DEFAULT_RESOLUTION;
        this.subImgCharMatcher = new SubImgCharMatcher(convertToCharArray(this.charset));
        this.asciiOutput = new ConsoleAsciiOutput();
        this.singleton = AsciiArtSingleton.getInstance();
    }

    /**
     * Converts a TreeSet of characters to a character array.
     *
     * @param charset the character set to convert.
     * @return an array of characters.
     */
    private char[] convertToCharArray(TreeSet<Character> charset) {
        char[] charArray = new char[charset.size()];
        int i = 0;
        for (Character c : charset) {
            charArray[i++] = c;
        }
        return charArray;
    }

    /**
     * Runs the shell and processes user input commands.
     *
     * @param imageName the name of the image file to process.
     * @throws IOException if an error occurs while reading the image file.
     */
    public void run(String imageName) throws IOException {
        createImage(imageName);
        System.out.print(Constants.ENTER_MESSAGE);
        String input = KeyboardInput.readLine();
        while (!input.equals(Constants.EXIT_INPUT)) {
            if (input.equals(Constants.CHAR_INPUT)) {
                printChars();
            } else if (input.startsWith(Constants.ADD_INPUT)) {
                handleAddCommand(input);
            } else if (input.startsWith(Constants.REMOVE_INPUT)) {
                handleRemoveCommand(input);
            } else if (input.startsWith(Constants.RES_INPUT)) {
                handleResCommand(input);
            } else if (input.equals(Constants.RES_INPUT.strip())) {
                System.out.println(Constants.NEW_RES_MESSAGE + resolution);
            } else if (input.startsWith(Constants.ROUND_INPUT)) {
                handleRoundCommand(input);
            } else if (input.startsWith(Constants.OUTPUT_INPUT)) {
                handleOutputCommand(input);
            } else if (input.equals(Constants.ASCII_ART_INPUT.strip()) || input.startsWith(Constants.ASCII_ART_INPUT)) {
                AsciiArtAlgorithm asciiArtAlgorithm = new AsciiArtAlgorithm(image, resolution, subImgCharMatcher);
                char[][] resultImage = asciiArtAlgorithm.run();
                this.asciiOutput.out(resultImage);
            } else {
                System.out.println(Constants.INCORRECT_COMMAND);
            }
            System.out.print(Constants.ENTER_MESSAGE);
            input = KeyboardInput.readLine();
        }
    }

    /**
     * Handles the output command to change the ASCII art output method.
     *
     * @param input the user input specifying the new output method.
     */
    private void handleOutputCommand(String input) {
        try {
            String[] parts = input.split(" ");
            String resArg = parts[1];
            AsciiOutputFactory factory = new AsciiOutputFactory();
            this.asciiOutput = factory.buildAsciiOutput(resArg);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Handles the command to set the rounding strategy for character matching.
     *
     * @param input the user input specifying the rounding strategy.
     */
    private void handleRoundCommand(String input) {
        try {
            String[] parts = input.split(" ");
            String resArg = parts[1];
            subImgCharMatcher.setRoundStrategy(resArg);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Creates and prepares the image for ASCII art generation.
     *
     * @param imageName the name of the image file.
     * @throws IOException if the image cannot be read.
     */
    private void createImage(String imageName) throws IOException {
        image = new Image(imageName);
        image = ImageEditor.padImage(image);
        minCharsInRow = Math.max(1, image.getWidth() / image.getHeight());
        maxCharsInRow = image.getWidth();
    }

    /**
     * Handles the resolution command to adjust the resolution of the ASCII art.
     *
     * @param input the user input specifying the resolution change.
     */
    private void handleResCommand(String input) {
        try {
            String[] parts = input.split(" ");
            String resArg = parts[1];
            changeRes(resArg);
            System.out.println(Constants.NEW_RES_MESSAGE + resolution);
        } catch (BoundariesResolutionException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Changes the resolution of the ASCII art.
     *
     * @param resArg the resolution adjustment argument (e.g., "up" or "down").
     * @throws IllegalArgumentException if the resolution argument is invalid.
     */
    private void changeRes(String resArg) throws IllegalArgumentException {
        if (resArg.equals(Constants.RES_UP)) {
            if (maxCharsInRow < resolution * 2) {
                throw new BoundariesResolutionException();
            }
            resolution *= 2;
        } else if (resArg.equals(Constants.RES_DOWN)) {
            if (minCharsInRow * 2 > resolution) {
                throw new BoundariesResolutionException();
            }
            resolution /= 2;
        } else {
            throw new IllegalArgumentException(Constants.incorrectFormatMessage(Constants.CHANGE_RESOLUTION));
        }
    }

    /**
     * Handles the command to remove characters from the character set.
     *
     * @param input the user input specifying the characters to remove.
     */
    private void handleRemoveCommand(String input) {
        try {
            String[] parts = input.split(" ");
            String removeArg = parts[1];
            removeFromCharset(removeArg);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Removes characters from the character set based on the input argument.
     *
     * @param removeArg the argument specifying which characters to remove.
     */
    private void removeFromCharset(String removeArg) {
        if (removeArg.equals(Constants.ALL_ARG)) {
            removeAllChars();
            return;
        }
        if (removeArg.equals(Constants.SPACE_ARG)) {
            removeFromTreeAndMatcher(' ');
            return;
        }
        if (removeArg.length() == 3 && isValidRange(removeArg)) {
            removeCharInRange(removeArg);
            return;
        }
        if (removeArg.length() != 1 || !isValidChar(removeArg.charAt(0))) {
            throw new IllegalArgumentException(Constants.incorrectFormatMessage(Constants.REMOVE_INPUT.strip()));
        }
        this.removeFromTreeAndMatcher(removeArg.charAt(0));
    }

    /**
     * Removes a range of characters from the character set.
     *
     * @param removeArg the argument specifying the character range to remove.
     */
    private void removeCharInRange(String removeArg) {
        char startRange = (char) Math.min(removeArg.charAt(0), removeArg.charAt(2));
        char endRange = (char) Math.max(removeArg.charAt(0), removeArg.charAt(2));
        for (char c = startRange; c <= endRange; c++) {
            this.removeFromTreeAndMatcher(c);
        }
    }

    /**
     * Removes all characters from the character set.
     */
    private void removeAllChars() {
        for (char i = Constants.MIN_ASCII_VAL; i <= Constants.MAX_ASCII_VAL; i++) {
            this.removeFromTreeAndMatcher(i);
        }
    }

    /**
     * Handles the command to add characters to the character set.
     *
     * @param input the user input specifying the characters to add.
     */
    private void handleAddCommand(String input) {
        try {
            String[] parts = input.split(" ");
            String addArg = parts[1];
            addToCharset(addArg);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Adds characters to the character set based on the input argument.
     *
     * @param addArg the argument specifying which characters to add.
     * @throws IllegalArgumentException if the argument format is invalid.
     */
    private void addToCharset(String addArg) throws IllegalArgumentException {
        if (addArg.equals(Constants.ALL_ARG)) {
            addAllChars();
            return;
        }
        if (addArg.equals(Constants.SPACE_ARG)) {
            addToTreeAndMatcher(' ');
            return;
        }
        if (addArg.length() == 3 && isValidRange(addArg)) {
            addCharInRange(addArg);
            return;
        }
        if (addArg.length() != 1 || !isValidChar(addArg.charAt(0))) {
            throw new IllegalArgumentException(Constants.incorrectFormatMessage(Constants.ADD_INPUT.strip()));
        }
        addToTreeAndMatcher(addArg.charAt(0));
    }

    /**
     * Adds all valid ASCII characters to the character set.
     */
    private void addAllChars() {
        for (char i = Constants.MIN_ASCII_VAL; i <= Constants.MAX_ASCII_VAL; i++) {
            addToTreeAndMatcher(i);
        }
    }

    /**
     * Adds a range of characters to the character set.
     *
     * @param addArg the argument specifying the character range to add.
     */
    private void addCharInRange(String addArg) {
        char startRange = (char) Math.min(addArg.charAt(0), addArg.charAt(2));
        char endRange = (char) Math.max(addArg.charAt(0), addArg.charAt(2));
        for (char c = startRange; c <= endRange; c++) {
            addToTreeAndMatcher(c);
        }
    }

    /**
     * Checks if a character range argument is valid.
     *
     * @param range the range argument to validate.
     * @return true if the range is valid, false otherwise.
     */
    private boolean isValidRange(String range) {
        return (isValidChar(range.charAt(0)) && range.charAt(1) == '-' && isValidChar(range.charAt(2)));
    }

    /**
     * Checks if a character is within the valid ASCII range.
     *
     * @param c the character to validate.
     * @return true if the character is valid, false otherwise.
     */
    private boolean isValidChar(char c) {
        return (c <= Constants.MAX_ASCII_VAL && c >= Constants.MIN_ASCII_VAL);
    }

    /**
     * Adds a character to the character set and updates the matcher and singleton.
     *
     * @param c the character to add.
     */
    private void addToTreeAndMatcher(char c) {
        this.charset.add(c);
        this.subImgCharMatcher.addChar(c);
        singleton.addToPrevCharset(c);
    }

    /**
     * Removes a character from the character set and updates the matcher and singleton.
     *
     * @param c the character to remove.
     */
    private void removeFromTreeAndMatcher(char c) {
        this.charset.remove(c);
        this.subImgCharMatcher.removeChar(c);
        singleton.removeFromPrevCharset(c);
    }

    /**
     * Prints the current character set to the console.
     */
    private void printChars() {
        for (char c : charset) {
            System.out.print(c + " ");
        }
        System.out.println();
    }

    /**
     * The entry point for the Shell application.
     *
     * @param args the command-line arguments (expects the image file name as the first argument).
     */
    public static void main(String[] args) {
        Shell shell = new Shell();
        try {
            shell.run(args[0]);
        } catch (IOException e) {
            System.out.println(Constants.INVALID_IMAGE_PATH);
        }
    }
}
