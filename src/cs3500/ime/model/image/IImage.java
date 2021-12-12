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

  /**
   * Returns a new downscale version of this {@code IImage}.
   *
   * @param newWidth  new width of the downscaled image
   * @param newHeight new height of the downscaled image
   * @return a new downscaled version of this {@code IImage}
   * @throws IllegalArgumentException if newWidth or newHeight are greater than the original, or are
   *                                  less than 0
   */
  IImage downscale(int newWidth, int newHeight) throws IllegalArgumentException;

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
  IImage applyTransform(double[][] transformKernel, boolean[][] mask)
      throws IllegalArgumentException;

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
  IImage applyFilter(double[][] filterKernel, boolean[][] mask) throws IllegalArgumentException;

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
  IImage getComponent(GreyscaleComponent component, boolean[][] mask)
      throws IllegalArgumentException;
}
