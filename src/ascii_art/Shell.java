package ascii_art;

import constants.Constants;
import exceptions.InvalidAddException;
import exceptions.InvalidRemoveException;
import image.Image;

import java.util.Arrays;
import java.io.IOException;
import java.util.TreeSet;

public class Shell {
    private final TreeSet<Character> charset;
    private final int resolution;

    public Shell() {
        this.charset = Constants.DEFAULT_CHARSET;
        this.resolution = Constants.DEFAULT_RESOLUTION;
    }

    public void run(String imageName) {
        System.out.println(Constants.ENTER_MESSAGE);
        String input = KeyboardInput.readLine();
        while (!input.equals(Constants.EXIT_INPUT)) {
            System.out.println(Constants.ENTER_MESSAGE);
            if (input.equals(Constants.CHAR_INPUT)) {
                printChars();
            } else if (input.startsWith(Constants.ADD_INPUT)) {
                handleAddCommand(input);
            } else if (input.startsWith(Constants.REMOVE_INPUT)) {
                handleRemoveCommand(input);
            } else if (input.startsWith(Constants.RES_INPUT)) {
                handleResCommand(input);
            } else {
                System.err.println(Constants.INCORRECT_COMMAND);
            }
            input = KeyboardInput.readLine();
        }
    }

    private void handleResCommand(String input) {
    }

    private void handleRemoveCommand(String input) {
        try {
            String[] parts = input.split(" ", 2); // Split into at most two parts
            if (parts.length < 2 || parts[1].isEmpty()) {
                throw new InvalidRemoveException(); // Treat missing argument as invalid
            }
            String removeArg = parts[1];
            removeFromCharset(removeArg);
        } catch (InvalidRemoveException e) {
            System.err.println(e.getMessage());
        }
    }

    private void removeFromCharset(String removeArg) {
        if (removeArg.equals(Constants.ALL_ARG)) {
            removeAllChars();
            return;
        }
        if (removeArg.length() == 3 && isValidRange(removeArg)) {
            removeCharInRange(removeArg);
            return;
        }
        if (removeArg.length() != 1 || !isValidChar(removeArg.charAt(0))) {
            throw new InvalidRemoveException();
        }
        this.charset.remove(removeArg.charAt(0));
    }

    private void removeCharInRange(String removeArg) {
        char startRange = (char) Math.min(removeArg.charAt(0), removeArg.charAt(2));
        char endRange = (char) Math.max(removeArg.charAt(0), removeArg.charAt(2));
        for (char c = startRange; c <= endRange; c++) {
            this.charset.add(c);
        }
    }

    private void removeAllChars() {
        for (char i = Constants.MIN_ASCII_VAL; i <= Constants.MAX_ASCII_VAL; i++) {
            this.charset.remove(i);
        }
    }

    private void handleAddCommand(String input) {
        try {
            String[] parts = input.split(" ", 2); // Split into at most two parts
            if (parts.length < 2 || parts[1].isEmpty()) {
                throw new InvalidAddException(); // Treat missing argument as invalid
            }
            String addArg = parts[1];
            addToCharset(addArg);
        } catch (InvalidAddException e) {
            System.err.println(e.getMessage());
        }
    }

    private void addToCharset(String addArg) throws InvalidAddException {
        if (addArg.equals(Constants.ALL_ARG)) {
            addAllChars();
            return;
        }
        if (addArg.length() == 3 && isValidRange(addArg)) {
            addCharInRange(addArg);
            return;
        }
        if (addArg.length() != 1 || !isValidChar(addArg.charAt(0))) {
            throw new InvalidAddException();
        }
        this.charset.add(addArg.charAt(0));
    }


    private void addCharInRange(String addArg) {
        char startRange = (char) Math.min(addArg.charAt(0), addArg.charAt(2));
        char endRange = (char) Math.max(addArg.charAt(0), addArg.charAt(2));
        for (char c = startRange; c <= endRange; c++) {
            this.charset.add(c);
        }
    }

    private boolean isValidRange(String range) {
        return (isValidChar(range.charAt(0)) && range.charAt(1) == '-' && isValidChar(range.charAt(2)));
    }

    private boolean isValidChar(char c) {
        return (c > Constants.MAX_ASCII_VAL || c < Constants.MIN_ASCII_VAL);
    }

    private void addAllChars() {
        for (char i = Constants.MIN_ASCII_VAL; i <= Constants.MAX_ASCII_VAL; i++) {
            this.charset.add(i);
        }
    }

    private void printChars() {
        for (char c : charset) {
            System.out.print(c + " ");
        }
    }


    public static void main(String[] args) throws IOException {
        Image image = new Image(args[0]);
        int resolution = Integer.parseInt(args[1]);
        Shell shell = new Shell();
//        AsciiArtAlgorithm alg = new AsciiArtAlgorithm(image, resolution, charset);
//        char[][] result = alg.run();
//        ConsoleAsciiOutput consoleAsciiOutput = new ConsoleAsciiOutput();
//        consoleAsciiOutput.out(result);
//        HtmlAsciiOutput htmlAsciiOutput = new HtmlAsciiOutput("catOutput.html","Courier New");
//        htmlAsciiOutput.out(result);
    }

}
