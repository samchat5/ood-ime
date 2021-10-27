package cs3500.ime.pixel;

import cs3500.ime.GreyscaleComponent;
import java.util.Arrays;
import java.util.Objects;

/**
 * Concrete class for an {@code IPixel}. Has a red, green, and blue value, each in the range of [0,
 * 2^bits - 1]. Can be brightened, darkened, or greyscaled.
 */
public class Pixel implements IPixel {

  private final int red;
  private final int blue;
  private final int green;
  private final int bits;

  public Pixel(int val) throws IllegalArgumentException {
    this(val, val, val);
  }

  public Pixel(int red, int green, int blue) throws IllegalArgumentException {
    this.bits = 8;
    if (outOfRange(red, green, blue)) {
      throw new IllegalArgumentException("Invalid values for red, green, and/or blue.");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;

  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof IPixel)) {
      return false;
    }
    Pixel that = (Pixel) o;
    return this.red == that.red && this.green == that.green && this.blue == that.blue;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.red, this.green, this.blue, this.bits);
  }

  @Override
  public String toString() {
    return "(" + red + ", " + green + ", " + blue + ") with " + bits + " bits";
  }

  private boolean outOfRange(int... vals) {
    return Arrays.stream(vals).anyMatch((val) -> (val < 0) || (val > Math.pow(2, bits) - 1));
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
    switch (component) {
      case RED:
        return new Pixel(red);
      case BLUE:
        return new Pixel(blue);
      case GREEN:
        return new Pixel(green);
      default:
        return null;
    }
  }
}
