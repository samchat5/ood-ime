package cs3500.ime.model.image;

import cs3500.ime.model.GreyscaleComponent;
import cs3500.ime.model.image.pixel.IPixel;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

/**
 * Concrete class representing a PPM image. Has an array of pixels, a width, and a height. An image
 * can have all its individual pixels brightened, darkened, or be flipped vertically/horizontally.
 */
public class PPMImage implements IImage {

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
  public PPMImage(int height, int width, IPixel[][] pixelArray) throws IllegalArgumentException {
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
    PPMImage that = (PPMImage) o;
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
    return new PPMImage(this.height, this.width, newPixelArray);

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
    return new PPMImage(this.height, this.width, newPixelArray);
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
    return new PPMImage(this.height, this.width, newPixelArray);
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
    return new PPMImage(this.height, this.width, newPixelArray);
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
