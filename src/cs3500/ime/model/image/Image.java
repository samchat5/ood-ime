package cs3500.ime.model.image;

import cs3500.ime.model.GreyscaleComponent;
import cs3500.ime.model.image.pixel.IPixel;
import cs3500.ime.model.image.pixel.Pixel;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

/**
 * Concrete class representing an image. Has an array of pixels, a width, and a height. An image can
 * have all its individual pixels brightened, darkened, flipped vertically/horizontally, filtered
 * (blurred/sharpened), or sepia-toned.
 */
public class Image implements IImage {

  private final int height;
  private final int width;
  private final IPixel[][] pixelArray;

  /**
   * Constructor for {@code Image}, that takes in a 2d array of {@code IPixel}s, and the size of the
   * image.
   *
   * @param height     image height
   * @param width      image width
   * @param pixelArray pixel array
   * @throws IllegalArgumentException if the height and width given don't match that of the array
   */
  public Image(int height, int width, IPixel[][] pixelArray) throws IllegalArgumentException {
    this.height = height;
    this.width = width;
    if (!heightWidthMatch(height, width, pixelArray)) {
      throw new IllegalArgumentException("Height/width mismatch.");
    }
    this.pixelArray = pixelArray;
  }

  private boolean heightWidthMatch(int height, int width, IPixel[][] pixelArray) {
    if (pixelArray.length == height) {
      return pixelArray.length == 0 ? width == 0 : pixelArray[0].length == width;
    }
    return false;
  }

  /**
   * Returns true if {@code this} and the given object match.
   *
   * @param o object to match
   * @return true if equal.
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof IImage)) {
      return false;
    }
    Image that = (Image) o;
    return width == that.width && height == that.height && Arrays.deepEquals(pixelArray,
        that.pixelArray);
  }

  /**
   * Returns the hashcode for this object.
   *
   * @return hashcode as an int
   */
  @Override
  public int hashCode() {
    return Objects.hash(width, height, Arrays.deepHashCode(pixelArray));
  }

  /**
   * Returns the given greyscale component of this image.
   *
   * @param component component to retrieve, (e.g. red component or value component)
   * @return a new image object representing this image, but greyscaled
   */
  @Override
  public IImage getComponent(GreyscaleComponent component) {
    IPixel[][] newPixelArray = new IPixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        IPixel foo = pixelArray[i][j];
        newPixelArray[i][j] = foo.getComponent(component);
      }
    }
    return new Image(this.height, this.width, newPixelArray);

  }

  /**
   * Returns a brightened version of this image.
   *
   * @param value value to brighten
   * @return a new image object representing this image, but brightened
   */
  @Override
  public IImage brighten(int value) {
    IPixel[][] newPixelArray = new IPixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        IPixel foo = pixelArray[i][j];
        newPixelArray[i][j] = foo.brighten(value);
      }
    }
    return new Image(this.height, this.width, newPixelArray);
  }

  /**
   * Flip this image horizontally.
   *
   * @return a new image object representing this image, but flipped horizontally
   */
  @Override
  public IImage horizontalFlip() {
    IPixel[][] newPixelArray = new IPixel[height][width];
    for (int i = 0; i < height; i++) {
      IPixel[] verticalLine = pixelArray[i].clone();
      Collections.reverse(Arrays.asList(verticalLine));
      newPixelArray[i] = verticalLine;
    }
    return new Image(this.height, this.width, newPixelArray);
  }

  /**
   * Flip this image vertically.
   *
   * @return a new image object representing this image, but flipped vertically
   */
  @Override
  public IImage verticalFlip() {
    IPixel[][] newPixelArray = pixelArray.clone();
    Collections.reverse(Arrays.asList(newPixelArray));
    return new Image(this.height, this.width, newPixelArray);
  }

  /**
   * Applies filterKernel to this IImage to create a new filtered IImage
   *
   * @param filterKernel filter scaling
   * @return a new filtered image
   */
  @Override
  public IImage applyFilter(double[][] filterKernel) {
    int height = filterKernel.length;
    int width = filterKernel[0].length;
    int leftBound = -height / 2;
    int rightBound = height / 2;
    int topBound = -width / 2;
    int bottomBound = width / 2;

    IPixel[][] newPixelArray = new IPixel[this.height][this.width];

    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        double[] sum = {0, 0, 0};
        for (int k = topBound; k <= bottomBound; k++) {
          for (int l = leftBound; l <= rightBound; l++) {
            int pixelRow = k + i;
            int pixelCol = l + j;
            if (pixelRow > 0 && pixelRow < this.height && pixelCol > 0 && pixelCol < this.width) {
              int[] rgb = pixelArray[pixelRow][pixelCol].getValues();
              sum[0] += filterKernel[k + height / 2][l + width / 2] * rgb[0];
              sum[1] += filterKernel[k + height / 2][l + width / 2] * rgb[1];
              sum[2] += filterKernel[k + height / 2][l + width / 2] * rgb[2];
            }
          }
        }
        newPixelArray[i][j] = new Pixel(Math.max(Math.min((int) sum[0], 255), 0),
            Math.max(Math.min((int) sum[1], 255), 0), Math.max(Math.min((int) sum[2], 255), 0));
      }
    }
    return new Image(this.height, this.width, newPixelArray);
  }

  /**
   * Applies filterKernel to this IIMage to create a new color-transformed IIMage
   *
   * @param transformKernel kernel to use in color transformation
   * @return a new filtered image
   */
  @Override
  public IImage applyTransform(double[][] transformKernel) {
    return null;
  }

  /**
   * Returns the string representing the encoding for this Image into a PPM file. For a PPM file,
   * this is the width, height, and max RGB value, followed by the RGB values of each pixel all on
   * separate lines.
   *
   * @return string encoding
   */
  @Override
  public String toString() {
    StringBuilder output = new StringBuilder();
    output.append(String.format("%d %d\n255\n", width, height));
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        IPixel foo = pixelArray[i][j];
        output.append(foo.toString());
      }
    }
    return output.toString();
  }
}
