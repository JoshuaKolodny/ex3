package ascii_art;

import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.Image;
import image.ImageEditor;
import image_char_matching.SubImgCharMatcher;

import java.io.IOException;

public class AsciiArtAlgorithm {
    private final Image image;
    private final int resolution;
    private final char[] charset;

    public AsciiArtAlgorithm(Image image, int resolution, char[] charset) {
        this.image = image;
        this.resolution = resolution;
        this.charset = charset;
    }

    public char[][] run() {
        SubImgCharMatcher matcher = new SubImgCharMatcher(this.charset);
        Image paddedImage = ImageEditor.padImage(image);
        Image[][] subImages = ImageEditor.createSubImages(paddedImage, resolution);
        char[][] resultImage = new char[subImages.length][subImages[0].length];
        for (int i = 0; i < subImages.length; i++) {
            for (int j = 0; j < subImages[0].length; j++) {
                double subImageBrightness = ImageEditor.calculateBrightness(subImages[i][j]);
                resultImage[i][j] = matcher.getCharByImageBrightness(subImageBrightness);
            }
        }
        return resultImage;
    }


}
