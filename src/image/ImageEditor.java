package image;

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
        int horizontalPadding = newWidth - image.getWidth() / 2;
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


}
