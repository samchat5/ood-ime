package cs3500.ime.image;

import cs3500.ime.GreyscaleComponent;

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
}
