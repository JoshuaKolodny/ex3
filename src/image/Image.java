package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A package-private class of the package image.
 * @author Dan Nirel
 */
public class Image {

    /** A 2D array representing the pixel colors of the image. */
    private final Color[][] pixelArray;

    /** The width of the image in pixels. */
    private final int width;

    /** The height of the image in pixels. */
    private final int height;

    /**
     * Constructs an Image object by loading pixel data from a file.
     *
     * @param filename the path to the image file.
     * @throws IOException if an error occurs while reading the file.
     */
    public Image(String filename) throws IOException {
        BufferedImage im = ImageIO.read(new File(filename));
        width = im.getWidth();
        height = im.getHeight();

        pixelArray = new Color[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixelArray[i][j] = new Color(im.getRGB(j, i));
            }
        }
    }

    /**
     * Constructs an Image object using a pre-defined pixel array and dimensions.
     *
     * @param pixelArray a 2D array of {@link Color} objects representing the image pixels.
     * @param width the width of the image.
     * @param height the height of the image.
     */
    public Image(Color[][] pixelArray, int width, int height) {
        this.pixelArray = pixelArray;
        this.width = width;
        this.height = height;
    }

    /**
     * Retrieves the width of the image.
     *
     * @return the width of the image in pixels.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Retrieves the height of the image.
     *
     * @return the height of the image in pixels.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Retrieves the color of a specific pixel in the image.
     *
     * @param x the row index of the pixel.
     * @param y the column index of the pixel.
     * @return the {@link Color} of the specified pixel.
     */
    public Color getPixel(int x, int y) {
        return pixelArray[x][y];
    }

    /**
     * Saves the current image to a file in JPEG format.
     *
     * @param fileName the name of the output file (without extension).
     */
    public void saveImage(String fileName) {
        BufferedImage bufferedImage = new BufferedImage(pixelArray[0].length, pixelArray.length,
                BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < pixelArray.length; x++) {
            for (int y = 0; y < pixelArray[x].length; y++) {
                bufferedImage.setRGB(y, x, pixelArray[x][y].getRGB());
            }
        }
        File outputfile = new File(fileName + ".jpeg");
        try {
            ImageIO.write(bufferedImage, "jpeg", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

