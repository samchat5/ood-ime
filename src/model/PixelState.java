package model;

/**
 * This interface represents operations that can be used to monitor the state of a pixel, without
 * changing it.
 */
public interface PixelState {

  /**
   * Gets the red component of a pixel.
   *
   * @return An integer representing the red component of a pixel.
   */
  int getRed();

  /**
   * Gets the green component of a pixel.
   *
   * @return An integer representing the green component of a pixel.
   */
  int getGreen();

  /**
   * Gets the blue component of a pixel.
   *
   * @return An integer representing the blue component of a pixel.
   */
  int getBlue();

}
