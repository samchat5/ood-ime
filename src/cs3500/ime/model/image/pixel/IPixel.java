package cs3500.ime.model.image.pixel;

import cs3500.ime.model.GreyscaleComponent;

/**
 * Interface to represent a pixel. Concrete classes will have integer values that represent each
 * unique pixel. A pixel can be brightened/darkened, and greyscaled.
 */
public interface IPixel {

  /**
   * Brighten/darken this {@code IPixel} by the given amount, and return the new pixel.
   *
   * @param value amount to brighten/darken by
   * @return new pixel
   */
  IPixel brighten(int value);

  /**
   * Get the given greyscale component of this pixel, and return the new pixel.
   *
   * @param component component type to evaluate
   * @return new pixel
   */
  IPixel getComponent(GreyscaleComponent component);

  /**
   * Get the RGB values of this pixel, represented as an int array.
   *
   * @return values of this pixel as an int array
   */
  int[] getValues();

  IPixel applyColorTransform(double[][] kernel);
}
