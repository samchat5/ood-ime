package model;

import java.util.Objects;

/**
 * This class is and implementation of the Pixel interface. It represents an RGB pixel with the all
 * the methods that can be used to modify a pixel. This class has three fields: red, green, and
 * blue.
 */
public class PixelRGB implements Pixel {

  private int red;
  private int blue;
  private int green;

  /**
   * This constructor creates a pixel with the following values: red, green and blue.
   *
   * @param red   the red component of a pixel.
   * @param green the green component of a pixel.
   * @param blue  the blue component of a pixel.
   * @throws IllegalArgumentException if the red, blue, or green values are invalid.
   */
  public PixelRGB(int red, int green, int blue) throws IllegalArgumentException {
    //red blue and green are invariants: value must be an integer between 0 and 255
    if (this.validRGB(red) && this.validRGB(blue) && this.validRGB(green)) {
      this.red = red;
      this.green = green;
      this.blue = blue;
    } else {
      throw new IllegalArgumentException("Red, blue and green values must be between 0 and 255");
    }

  }

  //Determines if a number is valid. A number is valid if it is <= 0 and >= 255.
  private boolean validRGB(int num) {
    return (num >= 0 && num <= 255);
  }


  @Override
  public int computeValue() {
    return Math.max(Math.max(this.red, this.green), this.blue);
  }

  @Override
  public int computeIntensity() {
    return ((this.red + this.green + this.blue) / 3);
  }

  @Override
  public int computeLuma() {
    double luma = (0.2126 * this.red + 0.7152 * this.green + 0.0722 * this.blue);
    return (int) Math.round(luma);
  }

  /**
   * Makes this pixel red by making the blue and green values equal to the red component.
   */
  @Override
  public void makePixelRed() {
    this.greyscale(this.red);
  }

  /**
   * Makes this pixel blue by making the red and green values equal to the blue component.
   */
  @Override
  public void makePixelBlue() {
    this.greyscale(this.blue);
  }

  /**
   * Makes this pixel green by making the blue and red values equal to the green component.
   */
  @Override
  public void makePixelGreen() {
    this.greyscale(this.green);
  }

  @Override
  public void makePixelBrighter(int amount) {
    if (amount < 0) {
      throw new IllegalArgumentException();
    }
    this.red = Math.min(this.red + amount, 255);
    this.green = Math.min(this.green + amount, 255);
    this.blue = Math.min(this.blue + amount, 255);
  }

  @Override
  public void makePixelDarker(int amount) {
    if (amount < 0) {
      throw new IllegalArgumentException();
    }
    this.red = Math.max(this.red - amount, 0);
    this.green = Math.max(this.green - amount, 0);
    this.blue = Math.max(this.blue - amount, 0);
  }

  public void makePixelValue() {
    this.greyscale(this.computeValue());
  }

  public void makePixelIntensity() {
    this.greyscale(this.computeIntensity());
  }

  public void makePixelLuma() {
    this.greyscale(this.computeLuma());
  }

  /**
   * Makes the pixel a shade of gray based on the value given.
   *
   * @param value represents the value to set all three channels to.
   */
  private void greyscale(int value) {
    this.red = value;
    this.blue = value;
    this.green = value;
  }


  @Override
  public int getRed() {
    return this.red;
  }


  @Override
  public int getGreen() {
    return this.green;
  }

  @Override
  public int getBlue() {
    return this.blue;
  }

  /**
   * This method overrides equals. Determines if the given pixel is equal to this pixel.
   *
   * @param pixel the given pixel.
   * @return a boolean, true if they are equal. otherwise, returns false.
   */
  @Override
  public boolean equals(Object pixel) {
    if (!(pixel instanceof Pixel)) {
      return false;
    }
    return this.red == ((Pixel) pixel).getRed()
        && this.blue == ((Pixel) pixel).getBlue()
        && this.green == ((Pixel) pixel).getGreen();
  }

  /**
   * This method overrides hashcode using the same fields as equals.
   *
   * @return an integer representing the hashcode.
   */
  @Override
  public int hashCode() {
    return Objects.hash(red, blue, green);
  }

  @Override
  public String toString() {
    return "(" + this.red + ", " + this.green + ", " + this.blue + ")";
  }
}
