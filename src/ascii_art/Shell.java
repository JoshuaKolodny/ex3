package ascii_art;

import constants.Constants;
import exceptions.AddException;
import exceptions.BoundariesResolutionException;
import exceptions.RemoveException;
import exceptions.ResolutionException;
import image.Image;
import image.ImageEditor;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;
import java.util.TreeSet;

public class Shell {
    private final TreeSet<Character> charset;
    private final SubImgCharMatcher subImgCharMatcher;
    private int resolution;
    private Image image;
    private int minCharsInRow;
    private int maxCharsInRow;

    public Shell() {
        this.charset = Constants.DEFAULT_CHARSET;
        this.resolution = Constants.DEFAULT_RESOLUTION;
        this.subImgCharMatcher = new SubImgCharMatcher(convertToCharArray(this.charset));
    }

    private char[] convertToCharArray(TreeSet<Character> charset) {
        char[] charArray = new char[charset.size()];
        int i = 0;
        for (Character c : charset) {
            charArray[i++] = c;
        }
        return charArray;
    }

    public void run(String imageName) {
        try {
            createImage(imageName);
        } catch (IOException e) {
            System.out.println(Constants.INVALID_IMAGE_PATH);
        }
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
                System.out.println(Constants.INCORRECT_COMMAND);
            }
            input = KeyboardInput.readLine();
        }
    }

    private void createImage(String imageName) throws IOException {
        image = new Image(imageName);
        image = ImageEditor.padImage(image);
        minCharsInRow = Math.max(1, image.getWidth()/ image.getHeight());
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
        } catch (ResolutionException | BoundariesResolutionException e) {
            System.out.println(e.getMessage());
        }
    }

    private void changeRes(String resArg) throws ResolutionException {
        if (resArg.equals(Constants.RES_UP)) {
            if (maxCharsInRow < resolution * 2){
                throw new BoundariesResolutionException();
            }
            resolution *= 2;
        } else if (resArg.equals(Constants.RES_DOWN)) {
            if (minCharsInRow * 2 > resolution){
                throw new BoundariesResolutionException();
            }
            resolution /= 2;
        } else {
            throw new ResolutionException();
        }
    }

    private void handleRemoveCommand(String input) {
        try {
            String[] parts = input.split(" ", 2); // Split into at most two parts
            if (parts.length < 2 || parts[1].isEmpty()) {
                throw new RemoveException(); // Treat missing argument as invalid
            }
            String removeArg = parts[1];
            removeFromCharset(removeArg);
        } catch (RemoveException e) {
            System.out.println(e.getMessage());
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
            throw new RemoveException();
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
                throw new AddException(); // Treat missing argument as invalid
            }
            String addArg = parts[1];
            addToCharset(addArg);
        } catch (AddException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addToCharset(String addArg) throws AddException {
        if (addArg.equals(Constants.ALL_ARG)) {
            addAllChars();
            return;
        }
        if (addArg.length() == 3 && isValidRange(addArg)) {
            addCharInRange(addArg);
            return;
        }
        if (addArg.length() != 1 || !isValidChar(addArg.charAt(0))) {
            throw new AddException();
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
        return (c > Constants.MAX_ASCII_VAL || c < Constants.MIN_ASCII_VAL);
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
