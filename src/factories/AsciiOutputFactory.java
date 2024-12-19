package factories;

import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import constants.Constants;

/**
 * The AsciiOutputFactory class is responsible for creating instances of
 * {@link AsciiOutput} based on the specified output type.
 *
 * <p>This factory supports multiple output formats, such as HTML and console.
 * If an unsupported output type is specified, an {@link IllegalArgumentException}
 * is thrown with an appropriate error message.</p>
 *
 * @author Joshua Kolodny, Itamar Lev Ari
 */
public class AsciiOutputFactory {

    /**
     * Builds an {@link AsciiOutput} instance based on the provided output type.
     *
     * @param outputType the type of output desired (e.g., "html" or "console").
     * @return an {@link AsciiOutput} instance for the specified output type.
     * @throws IllegalArgumentException if the output type is not supported.
     */
    public AsciiOutput buildAsciiOutput(String outputType) {
        switch (outputType) {
            case "html":
                return new HtmlAsciiOutput(Constants.OUTPUT_FILE_NAME, Constants.HTML_FONT);
            case "console":
                return new ConsoleAsciiOutput();
            default:
                throw new IllegalArgumentException(Constants.incorrectFormatMessage
                        (Constants.OUTPUT_COMMAND));
        }
    }
}
