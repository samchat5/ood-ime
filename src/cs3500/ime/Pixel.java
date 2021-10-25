package cs3500.ime;

/**
 * Concrete class for an {@code IPixel}. Has a red, green, and blue value, each in the range of [0,
 * 255]. Can be brightened, darkened, or greyscaled.
 */
public class Pixel implements IPixel {

  private final int red;
  private final int blue;
  private final int green;

  public Pixel(int red, int green, int blue) throws IllegalArgumentException {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Brighten/darken this {@code Pixel} by the given amount, and return the new pixel.
   *
   * @param value amount to brighten/darken by
   * @return new pixel
   */
  @Override
  public IPixel brighten(int value) {
    return null;
  }

  /**
   * Get the given greyscale component of this pixel, and return the new pixel.
   *
   * @param component component type to evaluate
   * @return new pixel
   */
  @Override
  public IPixel getComponent(GreyscaleComponent component) {
    return null;
  }
}
