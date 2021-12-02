package model;

/**
 * This interface represents the methods that can be done on a pixel. It extends the PixelState
 * interface.
 */
public interface Pixel extends PixelState {

  /**
   * Finds the maximum value out of the red, green, and blue components of a pixel.
   * @return an int representing the value of a pixel.
   */
  public int computeValue();

  /**
   * Computes the average of the red, green, and blue components of a pixel.
   * @return an integer representing the intensity of a pixel.
   */
  public int computeIntensity();

  /**
   * Computes the brightness (Luma) of a pixel using the following formula:
   *  0.2126r+0.7152g+0.0722b, where r = red, g = green, b = blue values of a pixel.
   * @return an integer representing the luma of a pixel.
   */
  public int computeLuma();

  /**
   * Makes this pixel red by making the blue and green values equal to the red component.
   */
  public void makePixelRed();

  /**
   * Makes this pixel blue by making the red and green values equal to the blue component.
   */
  public void makePixelBlue();

  /**
   * Makes this pixel green by making the blue and red values equal to the green component.
   */
  public void makePixelGreen();

  /**
   * brightens this pixel by a given constant amount. If the brightens causes the rgb values to
   * increase past the 255 limit then the value rgb component will be set to 255.
   */
  public void makePixelBrighter(int amount);

  /**
   * darkens this pixel by a given constant amount. If the brightens causes the rgb values to
   * decrease below the 0 limit then the value rgb component will be set to 0.
   */
  public void  makePixelDarker(int amount);

  /**
   * Sets the red, green and blue components of this pixel to the Value.
   */
  public void  makePixelValue();

  /**
   * Sets the red, green and blue components of this pixel to the intensity.
   */
  public void makePixelIntensity();

  /**
   * Sets the red, green and blue components of this pixel to the luma.
   */
  public void makePixelLuma();
}
