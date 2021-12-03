package model;

/**
 * This interface represents the methods that can be done on a pixel. It extends the PixelState
 * interface.
 */
public interface Pixel extends PixelState {

  /**
   * Finds the maximum value out of the red, green, and blue components of a pixel.
   *
   * @return an int representing the value of a pixel.
   */
  int computeValue();

  /**
   * Computes the average of the red, green, and blue components of a pixel.
   *
   * @return an integer representing the intensity of a pixel.
   */
  int computeIntensity();

  /**
   * Computes the brightness (Luma) of a pixel using the following formula: 0.2126r+0.7152g+0.0722b,
   * where r = red, g = green, b = blue values of a pixel.
   *
   * @return an integer representing the luma of a pixel.
   */
  int computeLuma();

  /**
   * Makes this pixel red by making the blue and green values equal to the red component.
   */
  void makePixelRed();

  /**
   * Makes this pixel blue by making the red and green values equal to the blue component.
   */
  void makePixelBlue();

  /**
   * Makes this pixel green by making the blue and red values equal to the green component.
   */
  void makePixelGreen();

  /**
   * brightens this pixel by a given constant amount. If the brightens causes the rgb values to
   * increase past the 255 limit then the value rgb component will be set to 255.
   */
  void makePixelBrighter(int amount);

  /**
   * darkens this pixel by a given constant amount. If the brightens causes the rgb values to
   * decrease below the 0 limit then the value rgb component will be set to 0.
   */
  void makePixelDarker(int amount);

  /**
   * Sets the red, green and blue components of this pixel to the Value.
   */
  void makePixelValue();

  /**
   * Sets the red, green and blue components of this pixel to the intensity.
   */
  void makePixelIntensity();

  /**
   * Sets the red, green and blue components of this pixel to the luma.
   */
  void makePixelLuma();
}
