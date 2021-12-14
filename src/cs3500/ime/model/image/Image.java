package cs3500.ime.model.image;

import cs3500.ime.model.GreyscaleComponent;
import cs3500.ime.model.image.pixel.IPixel;
import cs3500.ime.model.image.pixel.Pixel;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

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
   * Applies filterKernel to this IImage to create a new filtered IImage, using the given mask. The
   * mask is a 2D array of booleans, of the same size as the image, indicating which pixels should
   * be greyscaled.
   *
   * @param component component to use in greyscale
   * @param mask      mask to use in greyscale
   * @return a new greyscaled image
   * @throws IllegalArgumentException if the mask is null and not the same size as the image
   */
  @Override
  public IImage getComponent(GreyscaleComponent component, boolean[][] mask)
      throws IllegalArgumentException {
    if (mask == null || mask.length != height || (mask.length != 0 && mask[0].length != width)) {
      throw new IllegalArgumentException("Mask must be the same size as the image.");
    }
    IPixel[][] newPixelArray = new IPixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (mask[i][j]) {
          IPixel foo = pixelArray[i][j];
          newPixelArray[i][j] = foo.getComponent(component);
        } else {
          newPixelArray[i][j] = pixelArray[i][j];
        }
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
   * Applies filterKernel to this IImage to create a new filtered IImage.
   *
   * @param filterKernel filter scaling
   * @return a new filtered image
   * @throws IllegalArgumentException if the kernel is null or invalid filter matrix
   */
  @Override
  public IImage applyFilter(double[][] filterKernel) throws IllegalArgumentException {
    if (filterKernel == null || filterKernel.length % 2 == 0 || filterKernel[0].length % 2 == 0) {
      throw new IllegalArgumentException("Invalid kernel dimensions.");
    }
    IPixel[][] newPixelArray = new IPixel[this.height][this.width];
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        convolveIteration(filterKernel, newPixelArray, i, j);
      }
    }
    return new Image(this.height, this.width, newPixelArray);
  }

  /**
   * Applies filterKernel to this IImage to create a new filtered IImage, using the given mask. The
   * mask is a 2D array of booleans, of the same size as the image, indicating which pixels should
   * be filtered.
   *
   * @param filterKernel filter scaling
   * @param mask         mask to use in filter
   * @return a new filtered image
   * @throws IllegalArgumentException if the kernel is null or invalid, or the mask is null and not
   *                                  the same size as the image
   */
  @Override
  public IImage applyFilter(double[][] filterKernel, boolean[][] mask)
      throws IllegalArgumentException {
    if (filterKernel == null || filterKernel.length % 2 == 0 || filterKernel[0].length % 2 == 0) {
      throw new IllegalArgumentException("Invalid kernel dimensions.");
    }
    if (mask == null || mask.length != height || (mask.length != 0 && mask[0].length != width)) {
      throw new IllegalArgumentException("Mask must be the same size as the image.");
    }
    IPixel[][] newPixelArray = new IPixel[this.height][this.width];
    for (int i = 0; i < this.height; i++) {
      for (int j = 0; j < this.width; j++) {
        if (mask[i][j]) {
          convolveIteration(filterKernel, newPixelArray, i, j);
        } else {
          newPixelArray[i][j] = this.pixelArray[i][j];
        }
      }
    }
    return new Image(this.height, this.width, newPixelArray);
  }

  /**
   * Applies a color transform to this image, using the mask. The mask is a 2D array of booleans, of
   * the same size as the image, indicating which pixels should be transformed.
   *
   * @param transformKernel kernel to use in color transformation
   * @param mask            mask to use in color transformation
   * @return a new transformed image
   * @throws IllegalArgumentException if the kernel is null or not 3x3, or the mask is null or not
   *                                  the same size as the image
   */
  @Override
  public IImage applyTransform(double[][] transformKernel, boolean[][] mask)
      throws IllegalArgumentException {
    if (transformKernel == null || transformKernel.length != 3 || transformKernel[0].length != 3) {
      throw new IllegalArgumentException("Kernel must be 3x3.");
    }
    if (mask == null || mask.length != height || (mask.length != 0 && mask[0].length != width)) {
      throw new IllegalArgumentException("Mask must be the same size as the image.");
    }
    IPixel[][] newPixelArray = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (mask[i][j]) {
          newPixelArray[i][j] = this.pixelArray[i][j].applyColorTransform(transformKernel);
        } else {
          newPixelArray[i][j] = this.pixelArray[i][j];
        }
      }
    }
    return new Image(height, width, newPixelArray);
  }

  /**
   * Applies filterKernel to this IIMage to create a new color-transformed IIMage.
   *
   * @param transformKernel kernel to use in color transformation
   * @return a new filtered image
   * @throws IllegalArgumentException if the kernel is null or not 3x3
   */
  @Override
  public IImage applyTransform(double[][] transformKernel) throws IllegalArgumentException {
    if (transformKernel == null || transformKernel.length != 3 || transformKernel[0].length != 3) {
      throw new IllegalArgumentException("Invalid kernel dimensions.");
    }
    IPixel[][] newPixelArray = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        newPixelArray[i][j] = this.pixelArray[i][j].applyColorTransform(transformKernel);
      }
    }
    return new Image(height, width, newPixelArray);
  }

  @Override
  public int getWidth() {
    return this.width;
  }

  @Override
  public int getHeight() {
    return this.height;
  }

  @Override
  public IPixel getPixelAt(int row, int col) throws IllegalArgumentException {
    if (row < 0 || row >= height) {
      throw new IllegalArgumentException("Specified row is out of range");
    }
    if (col < 0 || col >= width) {
      throw new IllegalArgumentException("Specified column is out of range");
    }
    IPixel target = pixelArray[row][col];
    int[] targetValues = target.getValues();
    return new Pixel(targetValues[0], targetValues[1], targetValues[2]);
  }

  /**
   * Returns a new downscale version of this {@code IImage}.
   *
   * @param newWidth  new width of the downscaled image
   * @param newHeight new height of the downscaled image
   * @return a new downscaled version of this {@code IImage}
   * @throws IllegalArgumentException if newWidth or newHeight are greater than the initial values,
   *                                  or are less than 0
   */
  @Override
  public IImage downscale(int newWidth, int newHeight) throws IllegalArgumentException {
    if (newWidth < 0 || newHeight < 0) {
      throw new IllegalArgumentException("New width and height must be greater than 0.");
    }
    if (newWidth > this.width || newHeight > this.height) {
      throw new IllegalArgumentException(String.format("New dimensions are greater than original "
          + "dimensions. Width cannot be greater than %d, was %d. Height cannot be greater than "
          + "%d, was %d.", this.width, newWidth, this.height, newHeight));
    }
    IPixel[][] arr = new Pixel[newHeight][newWidth];
    for (int y_p = 0; y_p < newHeight; y_p++) {
      for (int x_p = 0; x_p < newWidth; x_p++) {
        double x = (double) x_p / newWidth * width;
        double y = (double) y_p / newHeight * height;

        int[] a = getPixelAt((int) Math.floor(y), (int) Math.floor(x)).getValues();
        int[] b = getPixelAt((int) Math.floor(y), (int) Math.ceil(x)).getValues();
        int[] c = getPixelAt((int) Math.ceil(y), (int) Math.floor(x)).getValues();
        int[] d = getPixelAt((int) Math.ceil(y), (int) Math.ceil(x)).getValues();

        // edge case where pixels are all equal to each other. in this case, we just set the new
        // pixel equal to whatever the original pixel was.
        if (!Arrays.equals(a, b) && !Arrays.equals(a, c) && !Arrays.equals(b, c)) {
          BiFunction<Integer, Integer, Double> m_f = (a1, b1) -> b1 * (x - Math.floor(x)) + a1 * (
              Math.ceil(x) - x);
          BiFunction<Integer, Integer, Double> n_f = (c1, d1) -> d1 * (x - Math.floor(x)) + c1 * (
              Math.ceil(x) - x);
          BiFunction<Double, Double, Integer> c_p =
              (m, n) -> (int) (n * (y - Math.floor(y)) + m * (Math.ceil(y) - y));
          int[] colors = IntStream.rangeClosed(0, 2).map((i) -> c_p.apply(m_f.apply(a[i], b[i]),
              n_f.apply(c[i], d[i]))).toArray();

          arr[y_p][x_p] = new Pixel(colors[0], colors[1], colors[2]);
        } else {
          arr[y_p][x_p] = new Pixel(a[0], a[1], a[2]);
        }
      }
    }
    return new Image(newHeight, newWidth, arr);
  }

  private void convolveIteration(double[][] filterKernel, IPixel[][] newPixelArray, int i, int j) {
    int height = filterKernel.length;
    int width = filterKernel[0].length;
    int leftBound = -height / 2;
    int rightBound = height / 2;
    int topBound = -width / 2;
    int bottomBound = width / 2;
    double[] sum = {0, 0, 0};

    for (int k = topBound; k <= bottomBound; k++) {
      for (int l = leftBound; l <= rightBound; l++) {
        int pixelRow = k + i;
        int pixelCol = l + j;
        if (pixelRow >= 0 && pixelRow < this.height && pixelCol >= 0
            && pixelCol < this.width) {
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
