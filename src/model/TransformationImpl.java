package model;

/**
 * Represents an implementation of the transformation interface. Represents linear color
 * transformations and the associated methods.
 */
public class TransformationImpl implements Transformation {

  double[][] transformation;

  /**
   * Represents a type of transformation. Defined by the given array.
   *
   * @param transformation represents the array by which the color values of the pixel are
   *                       multiplied to achieve the transformation.
   */
  public TransformationImpl(double[][] transformation) {
    if (transformation.length != 3) {
      throw new IllegalStateException("dimensions must match number of colors in representation");
    }

    for (int r = 0; r < 3; r++) {
      if (transformation[r].length != 3) {
        throw new IllegalArgumentException("linear transformation must have 3 x 3 matrices");
      }
    }

    this.transformation = transformation;
  }

  /**
   * Applies the given transformation to the image. The transformation is applied based on the color
   * values of a pixel.
   *
   * @return the transformed image.
   */
  @Override
  public Image applyLinear(Image image) {
    int height = image.getImageHeight();
    int width = image.getImageWidth();
    Pixel[][] original = image.getImage();
    Pixel[][] newPixels = new Pixel[height][width];

    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        newPixels[r][c] = this.pixelTransform(original[r][c]);
      }
    }

    return new Image(newPixels);
  }

  private Pixel pixelTransform(Pixel pix) {
    int red;
    int green;
    int blue;

    red = (int) Math.floor(transformation[0][0] * pix.getRed()
        + transformation[0][1] * pix.getGreen()
        + transformation[0][2] * pix.getBlue());
    blue = (int) Math.floor(transformation[1][0] * pix.getRed()
        + transformation[1][1] * pix.getGreen()
        + transformation[1][2] * pix.getBlue());
    green = (int) Math.floor(transformation[2][0] * pix.getRed()
        + transformation[2][1] * pix.getGreen()
        + transformation[2][2] * pix.getBlue());

    red = this.range(red);
    green = this.range(green);
    blue = this.range(blue);

    return new PixelRGB(red, blue, green);
  }

  private int range(int value) {
    value = Math.min(value, 255);
    value = Math.max(value, 0);
    return value;
  }

}
