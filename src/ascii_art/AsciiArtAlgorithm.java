package ascii_art;

import image.Image;
import image.ImageEditor;
import image_char_matching.SubImgCharMatcher;


public class AsciiArtAlgorithm {
    private final Image image;
    private final int resolution;
    private final SubImgCharMatcher matcher;
    private final AsciiArtSingleton singleton;

    public AsciiArtAlgorithm(Image image, int resolution, SubImgCharMatcher matcher) {
        this.image = image;
        this.resolution = resolution;
        this.matcher = matcher;
        this.singleton = AsciiArtSingleton.getInstance();
    }

    public char[][] run() {
        // Case all parameters are the same
        if (resolution == singleton.getPrevResolution() && singleton.isSameCharset()) {
            return singleton.getPrevImage();
        }
        // Otherwise
        Image paddedImage = ImageEditor.padImage(image);
        Image[][] subImages = ImageEditor.createSubImages(paddedImage, resolution);
        char[][] resultImage = new char[subImages.length][subImages[0].length];
        // Case where resolution changed or charset changed
        double[][] prevSubImageBrightnesses = singleton.getPrevSubImagesBrightnesses();
        double[][] newSubImageBrightnesses = new double[subImages.length][subImages[0].length];
        for (int i = 0; i < subImages.length; i++) {
            for (int j = 0; j < subImages[0].length; j++) {
                double subImageBrightness = (resolution == singleton.getPrevResolution())
                        ? prevSubImageBrightnesses[i][j] : ImageEditor.calculateBrightness(subImages[i][j]);
                resultImage[i][j] = matcher.getCharByImageBrightness(subImageBrightness);
                newSubImageBrightnesses[i][j] = subImageBrightness;
            }
        }
        updateSingleton(resultImage, newSubImageBrightnesses);
        return resultImage;
    }

    private void updateSingleton(char[][] resultImage, double[][] subImageBrightnesses) {
        singleton.resetCharset();
        singleton.setPrevResolution(resolution);
        singleton.setPrevSubImagesBrightnesses(subImageBrightnesses);
        singleton.setPrevImage(resultImage);
    }
}

