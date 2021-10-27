package pixel;

/**
 * This class represents an RGBPixel
 */
public class RGBPixel extends Pixel{
  protected int redValue;
  protected int greenValue;
  protected int blueValue;

  public RGBPixel(int numBits, int redValue, int greenValue, int blueValue) {
    super(numBits);
    this.redValue = redValue;
    this.greenValue = greenValue;
    this.blueValue = blueValue;
  }


  @Override
  public void brighten(int scalar) {
    //TODO: Implement later on after figuring out what measure of brightness we're meant to use
  }

  /**
   * Value is defined as the max value of the three components of a pixel.
   * @return this pixel's value as defined above
   */
  private int value() {
    return Math.max(Math.max(redValue, greenValue), blueValue);
  }

  /**
   * Intensity is defined as the average of the three components for each pixel.
   * @return this pixel's intensity as defined above
   */
  private double intensity() {
    return (redValue + greenValue + blueValue) / 3.0;
  }

  /**
   * Luma is defined as the weighted sum 0.2126r + 0.7152g + 0.0722b.
   * @return this pixel's luma as defined above
   */
  private double luma() {
    return 0.2126 * redValue + 0.7152 * greenValue + 0.0722 * blueValue;
  }


}
