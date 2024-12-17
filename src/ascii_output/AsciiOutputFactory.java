package ascii_output;

import constants.Constants;

public class AsciiOutputFactory {

    public AsciiOutput buildAsciiOutput(String outputType) {
        switch (outputType) {
            case "html":
                return new HtmlAsciiOutput(Constants.OUTPUT_FILE_NAME, Constants.HTML_FONT);
            case "console":
                return new ConsoleAsciiOutput();
            default:
                throw new IllegalArgumentException(Constants.stringIncorrectFormatMessage(Constants.OUTPUT_COMMAND));
        }
    }

}
