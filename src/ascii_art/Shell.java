package ascii_art;

import ascii_output.AsciiOutput;
import ascii_output.AsciiOutputFactory;
import ascii_output.ConsoleAsciiOutput;
import constants.Constants;

import java.lang.IllegalArgumentException;

import exceptions.BoundariesResolutionException;
import image.Image;
import image.ImageEditor;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;
import java.util.TreeSet;

public class Shell {
    private final TreeSet<Character> charset;
    private final SubImgCharMatcher subImgCharMatcher;
    private AsciiOutput asciiOutput;
    private int resolution;
    private Image image;
    private int minCharsInRow;
    private int maxCharsInRow;


    public Shell() {
        this.charset = Constants.DEFAULT_CHARSET;
        this.resolution = Constants.DEFAULT_RESOLUTION;
        this.subImgCharMatcher = new SubImgCharMatcher(convertToCharArray(this.charset));
        this.asciiOutput = new ConsoleAsciiOutput();
    }

    private char[] convertToCharArray(TreeSet<Character> charset) {
        char[] charArray = new char[charset.size()];
        int i = 0;
        for (Character c : charset) {
            charArray[i++] = c;
        }
        return charArray;
    }

    public void run(String imageName) throws IOException{
        try {
            createImage(imageName);
        } catch (IOException e) {
            System.out.println(Constants.INVALID_IMAGE_PATH);
        }
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

    private void handleOutputCommand(String input) {
        try {
            String[] parts = input.split(" ", 2); // Split into at most two parts
            String resArg = parts[1];
            AsciiOutputFactory factory = new AsciiOutputFactory();
            this.asciiOutput = factory.buildAsciiOutput(resArg);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void handleRoundCommand(String input) {
        try {
            // check argument "round "
            String[] parts = input.split(" ", 2); // Split into at most two parts
            String resArg = parts[1];
            subImgCharMatcher.setRoundStrategy(resArg);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void createImage(String imageName) throws IOException {
        image = new Image(imageName);
        image = ImageEditor.padImage(image);
        minCharsInRow = Math.max(1, image.getWidth() / image.getHeight());
        maxCharsInRow = image.getWidth();
    }

    private void handleResCommand(String input) {
        try {
            String[] parts = input.split(" ", 2); // Split into at most two parts
            if (parts.length < 2 || parts[1].isEmpty()) {
                System.out.println(Constants.NEW_RES_MESSAGE + resolution);
            }
            String resArg = parts[1];
            changeRes(resArg);
            System.out.println(Constants.NEW_RES_MESSAGE + resolution);
        } catch (BoundariesResolutionException e) {
            System.out.println(e.getMessage());
        }
    }

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
            throw new IllegalArgumentException(Constants.stringIncorrectFormatMessage(Constants.RES_INPUT.strip()));
        }
    }

    private void handleRemoveCommand(String input) {
        try {
            String[] parts = input.split(" ", 2); // Split into at most two parts
            if (parts.length < 2 || parts[1].isEmpty()) {
                throw new IllegalArgumentException
                        (Constants.stringIncorrectFormatMessage(Constants.REMOVE_INPUT.strip()));
            }
            String removeArg = parts[1];
            removeFromCharset(removeArg);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

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
            throw new IllegalArgumentException(Constants.stringIncorrectFormatMessage(Constants.REMOVE_INPUT.strip()));
        }
        this.removeFromTreeAndMatcher(removeArg.charAt(0));
    }

    private void removeCharInRange(String removeArg) {
        char startRange = (char) Math.min(removeArg.charAt(0), removeArg.charAt(2));
        char endRange = (char) Math.max(removeArg.charAt(0), removeArg.charAt(2));
        for (char c = startRange; c <= endRange; c++) {
            this.removeFromTreeAndMatcher(c);
        }
    }

    private void removeAllChars() {
        for (char i = Constants.MIN_ASCII_VAL; i <= Constants.MAX_ASCII_VAL; i++) {
            this.removeFromTreeAndMatcher(i);
        }
    }

    private void handleAddCommand(String input) {
        try {
            String[] parts = input.split(" ", 2); // Split into at most two parts
            if (parts.length < 2 || parts[1].isEmpty()) {
                throw new IllegalArgumentException(Constants.stringIncorrectFormatMessage(Constants.ADD_INPUT.strip()));
            }
            String addArg = parts[1];
            addToCharset(addArg);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

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
            throw new IllegalArgumentException(Constants.stringIncorrectFormatMessage(Constants.ADD_INPUT.strip()));
        }
        addToTreeAndMatcher(addArg.charAt(0));
    }

    private void addToTreeAndMatcher(char c) {
        this.charset.add(c);
        this.subImgCharMatcher.addChar(c);

    }

    private void removeFromTreeAndMatcher(char c) {
        this.charset.remove(c);
        this.subImgCharMatcher.removeChar(c);

    }

    private void addCharInRange(String addArg) {
        char startRange = (char) Math.min(addArg.charAt(0), addArg.charAt(2));
        char endRange = (char) Math.max(addArg.charAt(0), addArg.charAt(2));
        for (char c = startRange; c <= endRange; c++) {
            addToTreeAndMatcher(c);
        }
    }

    private boolean isValidRange(String range) {
        return (isValidChar(range.charAt(0)) && range.charAt(1) == '-' && isValidChar(range.charAt(2)));
    }

    private boolean isValidChar(char c) {
        return (c <= Constants.MAX_ASCII_VAL && c >= Constants.MIN_ASCII_VAL);
    }

    private void addAllChars() {
        for (char i = Constants.MIN_ASCII_VAL; i <= Constants.MAX_ASCII_VAL; i++) {
            addToTreeAndMatcher(i);
        }
    }

    private void printChars() {
        for (char c : charset) {
            System.out.print(c + " ");
        }
        System.out.println();
    }


    public static void main(String[] args) {
        Shell shell = new Shell();
        try {
        shell.run(args[0]);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
