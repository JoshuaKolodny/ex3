package image;

import constants.Constants;

import java.awt.*;

/**
 * The ImageEditor class provides utility methods for manipulating and processing
 * images, including padding images to dimensions that are powers of two,
 * creating sub-images, and calculating brightness.
 *
 * <p>These methods are used extensively in the ASCII art generation process to
 * ensure uniformity and accuracy in image representation.</p>
 *
 * @author Joshua Kolodny, Itamar Lev Ari
 */
public class ImageEditor {

    /**
     * Pads an image to ensure its dimensions are powers of two.
     * The padding is filled with white pixels.
     *
     * @param image the image to be padded.
     * @return a new padded Image object.
     */
    public static Image padImage(Image image) {
        Color[][] pixelArray = buildPaddedColorArray(image);
        return new Image(pixelArray, pixelArray.length, pixelArray[0].length);
    }

    /**
     * Finds the next power of two greater than or equal to the given dimension.
     *
     * @param oldDimension the original dimension.
     * @return the new dimension as a power of two.
     */
    private static int findNewDimension(int oldDimension) {
        int newDimension = 1;
        while (newDimension < oldDimension) {
            newDimension *= 2;
        }
        return newDimension;
    }

    /**
     * Creates a white pixel matrix with dimensions as powers of two,
     * based on the input image's dimensions.
     *
     * @param image the input image.
     * @return a matrix filled with white pixels.
     */
    private static Color[][] createWhiteMatrix(Image image) {
        Color[][] pixelArray = new Color[findNewDimension(image.getHeight())][findNewDimension(image.getWidth())];
        for (int i = 0; i < pixelArray.length; i++) {
            for (int j = 0; j < pixelArray[0].length; j++) {
                pixelArray[i][j] = Color.WHITE;
            }
        }
        return pixelArray;
    }

    /**
     * Calculates the padding dimensions needed to center the image
     * in a matrix with dimensions as powers of two.
     *
     * @param image the input image.
     * @return an array containing the vertical and horizontal padding dimensions.
     */
    private static int[] getPaddingDimensions(Image image) {
        int newHeight = findNewDimension(image.getHeight());
        int newWidth = findNewDimension(image.getWidth());
        int verticalPadding = (newHeight - image.getHeight()) / 2;
        int horizontalPadding = (newWidth - image.getWidth()) / 2;
        return new int[]{verticalPadding, horizontalPadding};
    }

    /**
     * Builds a padded color array for the image, centering the original image
     * and filling the rest with white pixels.
     *
     * @param image the input image.
     * @return a new pixel array with padding.
     */
    private static Color[][] buildPaddedColorArray(Image image) {
        Color[][] pixelArray = createWhiteMatrix(image);
        int verticalPadding = getPaddingDimensions(image)[0];
        int horizontalPadding = getPaddingDimensions(image)[1];
        for (int i = verticalPadding; i < image.getHeight() + verticalPadding; i++) {
            for (int j = horizontalPadding; j < image.getWidth() + horizontalPadding; j++) {
                pixelArray[i][j] = image.getPixel(i - verticalPadding, j - horizontalPadding);
            }
        }
        return pixelArray;
    }

    /**
     * Splits an image into smaller sub-images based on the specified resolution.
     *
     * @param image the input image to be divided.
     * @param resolution the number of sub-images per row/column.
     * @return a 2D array of sub-images.
     */
    public static Image[][] createSubImages(Image image, int resolution) {
        int subImageDimension = image.getWidth() / resolution;
        int subImagesMatrixHeight = image.getHeight() / subImageDimension;
        int subImagesMatrixWidth = image.getWidth() / subImageDimension;
        Image[][] subImagesArray = new Image[subImagesMatrixHeight][subImagesMatrixWidth];
        for (int i = 0; i < subImagesMatrixHeight; i++) {
            for (int j = 0; j < subImagesMatrixWidth; j++) {
                subImagesArray[i][j] = createSubImage(image, subImageDimension, i, j);
            }
        }
        return subImagesArray;
    }

    /**
     * Creates a single sub-image from the specified region of the input image.
     *
     * @param image the input image.
     * @param subImageDimension the dimensions of the sub-image.
     * @param imageRow the row index of the sub-image in the grid.
     * @param imageCol the column index of the sub-image in the grid.
     * @return a new Image object representing the sub-image.
     */
    private static Image createSubImage(Image image, int subImageDimension, int imageRow, int imageCol) {
        Color[][] pixelArray = new Color[subImageDimension][subImageDimension];
        int rowParam = imageRow * subImageDimension;
        int colParam = imageCol * subImageDimension;
        for (int row = 0; row < subImageDimension; row++) {
            for (int col = 0; col < subImageDimension; col++) {
                pixelArray[row][col] = image.getPixel(row + rowParam, col + colParam);
            }
        }
        return new Image(pixelArray, subImageDimension, subImageDimension);
    }

    /**
     * Calculates the average brightness of the given image.
     *
     * @param image the input image.
     * @return the average brightness value (normalized between 0 and 1).
     */
    public static double calculateBrightness(Image image) {
        double sumGreyPixels = 0;
        int imageHeight = image.getHeight();
        int imageWidth = image.getWidth();
        for (int i = 0; i < imageHeight; i++) {
            for (int j = 0; j < imageWidth; j++) {
                sumGreyPixels += calculateGreyPixel(image.getPixel(i, j));
            }
        }
        return (sumGreyPixels / (imageHeight * imageWidth)) / Constants.MAX_RGB_VAL;
    }

    /**
     * Calculates the grey pixel value using the weighted RGB components.
     *
     * @param pixel the color pixel.
     * @return the calculated grey pixel value.
     */
    private static double calculateGreyPixel(Color pixel) {
        return pixel.getRed() * Constants.RED_MULT + pixel.getGreen() * Constants.GREEN_MULT
                + pixel.getBlue() * Constants.BLUE_MULT;
    }
}
