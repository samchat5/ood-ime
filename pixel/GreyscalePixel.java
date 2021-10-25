package pixel;

/**
 * Class to represent a single Greyscale pixel
 */
public class GreyscalePixel extends Pixel {
  protected int pixelValue;

  public GreyscalePixel(int numBits, int pixelValue) {
    super(numBits);
    this.pixelValue = pixelValue;
  }

  @Override
  public void brighten(int scalar) {
    //TODO: Is brightening/darkening just changing the pixelValue by scalar?
    // I feel that numBits has to factor in somehow?
  }
}
