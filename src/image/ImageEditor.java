package image;

import constants.Constants;

import java.awt.*;

public class ImageEditor {

    public static Image padImage(Image image) {
        Color[][] pixelArray = buildPaddedColorArray(image);
        return new Image(pixelArray, pixelArray.length, pixelArray[0].length);
    }

    private static int findNewDimension(int oldDimension) {
        int newDimension = 1;
        while (newDimension < oldDimension) {
            newDimension *= 2;
        }
        return newDimension;
    }

    private static Color[][] createWhiteMatrix(Image image) {
        Color[][] pixelArray = new Color[findNewDimension(image.getHeight())][findNewDimension(image.getWidth())];
        for (int i = 0; i < pixelArray.length; i++) {
            for (int j = 0; j < pixelArray[0].length; j++) {
                pixelArray[i][j] = Color.WHITE;
            }
        }
        return pixelArray;
    }

    private static int[] getPaddingDimensions(Image image) {
        int newHeight = findNewDimension(image.getHeight());
        int newWidth = findNewDimension(image.getWidth());
        // Calculate padding from each side
        int verticalPadding = (newHeight - image.getHeight()) / 2;
        int horizontalPadding = (newWidth - image.getWidth()) / 2;
        return new int[]{verticalPadding, horizontalPadding};
    }

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

    private static double calculateGreyPixel(Color pixel) {
        return pixel.getRed() * Constants.RED_MULT + pixel.getGreen() * Constants.GREEN_MULT
                + pixel.getBlue() * Constants.BLUE_MULT;
    }

}
