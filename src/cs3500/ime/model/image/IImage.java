package cs3500.ime.model.image;

import cs3500.ime.model.GreyscaleComponent;
import cs3500.ime.model.image.pixel.IPixel;

/**
 * Interface for an Image, which contains a 2D array of {@code IPixel}s, and each concrete class
 * contain other attributes such as width, height, etc.
 */
public interface IImage {

  /**
   * Get that given {@code GreyscaleComponent} of this {@code IImage}.
   *
   * @param component component to retrieve, (e.g. red component or value component)
   * @return new {@code IImage} representing the given component of this {@code IImage}
   */
  IImage getComponent(GreyscaleComponent component);

  /**
   * Get this {@code IImage} brightened by the given value. A negative value represents darkening.
   *
   * @param value value to brighten by (clamped to -225 to 255)
   * @return new {@code IImage} that has been brightened by the given amount
   */
  IImage brighten(int value);

  /**
   * Get this {@code IImage} flipped horizontally.
   *
   * @return this {@code IImage} flipped horizontally.
   */
  IImage horizontalFlip();

  /**
   * Get this {@code IImage} flipped vertically.
   *
   * @return this {@code IImage} flipped vertically.
   */
  IImage verticalFlip();

  /**
   * Applies filterKernel to this IImage to create a new filtered IImage.
   *
   * @param filterKernel filter scaling
   * @return a new filtered image
   * @throws IllegalArgumentException if the kernel is null or invalid filter matrix
   */
  IImage applyFilter(double[][] filterKernel) throws IllegalArgumentException;

  /**
   * Applies filterKernel to this IIMage to create a new color-transformed IImage.
   *
   * @param transformKernel kernel to use in color transformation
   * @return a new filtered image
   * @throws IllegalArgumentException if the kernel is null or not 3x3
   */
  IImage applyTransform(double[][] transformKernel) throws IllegalArgumentException;

  /**
   * Getter method for image width.
   *
   * @return this image's numeric width
   */
  int getWidth();

  /**
   * Getter method for image height.
   *
   * @return this image's numeric height
   */
  int getHeight();

  /**
   * Returns a pixel at a specified position in the image.
   *
   * @param row 0 indexed vertical line position of the specified pixel
   * @param col 0 indexed horizontal line position of the specified pixel
   * @return a copy of the pixel at the given location of this image
   * @throws IllegalArgumentException if row or col are out of the image's range
   */
  IPixel getPixelAt(int row, int col) throws IllegalArgumentException;

}
