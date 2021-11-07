package cs3500.ime.model.image.pixel;

import cs3500.ime.model.GreyscaleComponent;
import java.util.Arrays;
import java.util.Objects;

/**
 * Concrete class for an {@code IPixel}. Has a red, green, and blue value, each in the range of [0,
 * 2^bits - 1]. Can be brightened, darkened, or greyscaled.
 */
public class Pixel implements IPixel {

  // INVARIANT: all these fields are clamped to 0 <= red, green, blue <= 255.
  private final int red;
  private final int blue;
  private final int green;

  /**
   * Constructor for a grey pixel, taking in only one value for all RGB fields.
   *
   * @param val grey scale value
   * @throws IllegalArgumentException iff the value is invalid (i.e. out of range)
   */
  public Pixel(int val) throws IllegalArgumentException {
    this(val, val, val);
  }

  /**
   * Constructor for a color pixel, taking in a value for the red, green, and blue component.
   *
   * @param red   red value
   * @param green green value
   * @param blue  blue value
   * @throws IllegalArgumentException iff any of the values are invalid (i.e. out of range)
   */
  public Pixel(int red, int green, int blue) throws IllegalArgumentException {
    if (outOfRange(red, green, blue)) {
      throw new IllegalArgumentException("Invalid values for red, green, and/or blue.");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Returns true if {@code this} is equal to the given object. For a pixel, this means the RGB
   * components are all the same
   *
   * @param o other object.
   * @return if the objects match
   */
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof IPixel)) {
      return false;
    }
    Pixel that = (Pixel) o;
    return this.red == that.red && this.green == that.green && this.blue == that.blue;
  }

  /**
   * Returns the hashcode for this object, as an int, based on its RGB values.
   *
   * @return hashcode
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.red, this.green, this.blue);
  }

  /**
   * Returns a string representation of this pixel, which is the RGB components on separate lines.
   *
   * @return string representation
   */
  @Override
  public String toString() {
    return String.format("%d\n%d\n%d\n", red, green, blue);
  }

  private boolean outOfRange(int... vals) {
    return Arrays.stream(vals).anyMatch((val) -> (val < 0) || (val > 255));
  }

  /**
   * Brighten/darken this {@code Pixel} by the given amount, and return the new pixel.
   *
   * @param value amount to brighten/darken by
   * @return new pixel
   */
  @Override
  public IPixel brighten(int value) {
    return new Pixel(brightenHelper(this.red, value), brightenHelper(this.green, value),
        brightenHelper(this.blue, value));
  }

  private int brightenHelper(int originalColor, int value) {
    int newColorVal = originalColor + value;
    if (newColorVal < 0) {
      return 0;
    } else {
      return Math.min(newColorVal, 255);
    }
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
      case VALUE:
        return new Pixel(Math.max(Math.max(this.red, this.green), this.blue));
      case INTENSITY:
        return new Pixel((this.red + this.green + this.blue) / 3);
      case LUMA:
        return new Pixel((int) (0.2126 * this.red + 0.7152 * this.green + 0.0722 * this.blue));
      default:
        return null;
    }
  }

  /**
   * Returns the RGB values of this pixel.
   *
   * @return values of the form [red, green, blue]
   */
  @Override
  public int[] getValues() {
    return new int[]{this.red, this.green, this.blue};
  }

  @Override
  public IPixel applyColorTransform(double[][] kernel) {
    int red = (int) (kernel[0][0] * this.red + kernel[0][1] * this.green
        + kernel[0][2] * this.blue);
    int green = (int) (kernel[1][0] * this.red + kernel[1][1] * this.green
        + kernel[1][2] * this.blue);
    int blue = (int) (kernel[2][0] * this.red + kernel[2][1] * this.green
        + kernel[2][2] * this.blue);

    return new Pixel(Math.max(Math.min(red, 255), 0), Math.max(Math.min(green, 255), 0),
        Math.max(Math.min(blue, 255), 0));
  }
}
