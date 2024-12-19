package ascii_art;

import image.Image;
import image.ImageEditor;
import image_char_matching.SubImgCharMatcher;

/**
 * The AsciiArtAlgorithm class generates ASCII art representations of an image.
 * It converts the image into a 2D character array based on brightness levels.
 * This class manages resolution, character matching, and uses a singleton to maintain state.
 *
 * @author Joshua Kolodny, Itamar Lev Ari
 */
public class AsciiArtAlgorithm {

    /** The input image to be processed. */
    private final Image image;

    /** The resolution of the ASCII art output. */
    private final int resolution;

    /** The matcher used to map brightness levels to characters. */
    private final SubImgCharMatcher matcher;

    /** Singleton instance for maintaining state across runs. */
    private final AsciiArtSingleton singleton;

    /**
     * Constructs a new AsciiArtAlgorithm instance with the given image, resolution, and matcher.
     *
     * @param image the input image to process.
     * @param resolution the desired resolution for ASCII art.
     * @param matcher the matcher for character selection based on brightness.
     */
    public AsciiArtAlgorithm(Image image, int resolution, SubImgCharMatcher matcher) {
        this.image = image;
        this.resolution = resolution;
        this.matcher = matcher;
        this.singleton = AsciiArtSingleton.getInstance();
    }

    /**
     * Runs the ASCII art generation algorithm and returns the resulting character array.
     *
     * @return a 2D character array representing the ASCII art.
     */
    public char[][] run() {
        // Case: All parameters remain the same
        if (resolution == singleton.getPrevResolution() && singleton.isSameCharset()) {
            return singleton.getPrevImage(); // Return cached result
        }

        // Adjust the image to fit the desired resolution
        Image paddedImage = ImageEditor.padImage(image);
        Image[][] subImages = ImageEditor.createSubImages(paddedImage, resolution);
        char[][] resultImage = new char[subImages.length][subImages[0].length];

        // Update brightness calculations and generate ASCII art
        double[][] prevSubImageBrightnesses = singleton.getPrevSubImagesBrightnesses();
        double[][] newSubImageBrightnesses = new double[subImages.length][subImages[0].length];

        for (int i = 0; i < subImages.length; i++) {
            for (int j = 0; j < subImages[0].length; j++) {
                // Use cached brightness if resolution hasn't changed
                double subImageBrightness = (resolution == singleton.getPrevResolution())
                        ? prevSubImageBrightnesses[i][j]
                        : ImageEditor.calculateBrightness(subImages[i][j]);

                // Map brightness to a character
                resultImage[i][j] = matcher.getCharByImageBrightness(subImageBrightness);

                // Store brightness for future use
                newSubImageBrightnesses[i][j] = subImageBrightness;
            }
        }

        // Update the singleton with the new results
        updateSingleton(resultImage, newSubImageBrightnesses);
        return resultImage;
    }

    /**
     * Updates the singleton with the latest results, including the generated ASCII art
     * and brightness values of sub-images.
     *
     * @param resultImage the generated ASCII art as a 2D character array.
     * @param subImageBrightnesses the brightness values of the sub-images.
     */
    private void updateSingleton(char[][] resultImage, double[][] subImageBrightnesses) {
        singleton.resetCharset(); // Reset the character set if needed
        singleton.setPrevResolution(resolution); // Update the previous resolution
        singleton.setPrevSubImagesBrightnesses(subImageBrightnesses); // Store brightness values
        singleton.setPrevImage(resultImage); // Cache the generated ASCII art
    }
}
