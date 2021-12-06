package model;

import java.util.Objects;

/**
 * This class is a mock used for testing.
 */
public class MockModel implements ImageModel {

  protected final StringBuilder log;

  public MockModel(StringBuilder log) {
    this.log = Objects.requireNonNull(log);
  }

  /**
   * Produces the image represented by the red values of the current image. The red values are
   * defined by the color representation.
   */
  @Override
  public void makeRed() {
    this.log.append("make red");
  }

  /**
   * Produces the image represented by the green values of the current image. The red values are
   * defined by the color representation.
   */
  @Override
  public void makeGreen() {
    this.log.append("make green");
  }

  /**
   * Produces the image represented by the blue values of the current image. The red values are
   * defined by the color representation.
   */
  @Override
  public void makeBlue() {
    this.log.append("make blue");
  }

  /**
   * Produces the image represented by the value of the current image. The value is the maximum
   * color component of each pixel.
   */
  @Override
  public void imageValue() {
    this.log.append("image value");
  }

  /**
   * Produces the image represented by the intensity of the current image. The intensity is the
   * average of all color components.
   */
  @Override
  public void imageIntensity() {
    this.log.append("image intensity");
  }

  /**
   * Produces the image represented by the luma of the current image. The luma is the weighted sum
   * of the color components.
   */
  @Override
  public void imageLuma() {
    this.log.append("image luma");
  }

  /**
   * Flips the image so that the left and right sides of the image are reversed. The vertical
   * component of each part of the image stays constant.
   */
  @Override
  public void flipHorizontal() {
    this.log.append("flip horizontal");
  }

  /**
   * Flips each image so that the top and bottom of the image are reversed. The horizontal component
   * of each part should stay constant.
   */
  @Override
  public void flipVertical() {
    this.log.append("flip vertical");
  }

  /**
   * Brightens the image by a specified amount. Brightens the image by increasing the color values.
   *
   * @param amount the amount to brighten the image by.
   */
  @Override
  public void brighten(int amount) {
    this.log.append(String.format("brighten %d", amount));
  }

  /**
   * Darkens the image by a specified amount. Darkens the image by decreasing the color values.
   *
   * @param amount the amount to darken the image by.
   */
  @Override
  public void darken(int amount) {
    this.log.append(String.format("darken %d", amount));
  }

  /**
   * Sets this image equal to the given image. This method is used for loading or overwriting an
   * image.
   *
   * @param image the image that needs to be loaded.
   */
  @Override
  public void overWriteImage(Image image) {
  }

  /**
   * Applies a blur effect to an image. Blur effect is based on a 3x3 kernel.
   */
  @Override
  public void gaussianBlur() {
    this.log.append("gaussian blur");
  }

  /**
   * Applies a sharpening effect to an image. Sharpening effect is based on 5x5 kernel.
   */
  @Override
  public void sharpen() {
    this.log.append("sharpen");
  }

  /**
   * Applies a linear filter to make the image greyscale; Effect is based on a matrix of values
   * applied to the image's color values.
   */
  @Override
  public void greyscale() {
    this.log.append("greyscale");
  }

  /**
   * Applies a linear filter to make the image sepia toned. Effect is based on a matrix of values
   * applied to the image's color values.
   */
  @Override
  public void sepia() {
    this.log.append("sepia");
  }

  /**
   * Gets the red component of the pixel at the given location within an image.
   *
   * @param row the row the pixel is in.
   * @param col the column the pixel is in.
   * @return an integer representing the red component.
   */
  @Override
  public int redValuePixel(int row, int col) {
    return 0;
  }

  /**
   * Gets the blue component of the pixel at the given location within an image.
   *
   * @param row the row the pixel is in.
   * @param col the column the pixel is in.
   * @return an integer representing the blue component.
   */
  @Override
  public int blueValuePixel(int row, int col) {
    return 0;
  }

  /**
   * Gets the green component of the pixel at the given location within an image.
   *
   * @param row the row the pixel is in.
   * @param col the column the pixel is in.
   * @return an integer representing the green component.
   */
  @Override
  public int greenValuePixel(int row, int col) {
    return 0;
  }

  /**
   * Gets the image of this RGBModel. This image is a copy of the image instead of the image itself.
   * This prevents a user from changing values in this image's array through access.
   *
   * @return the array representing this image.
   */
  @Override
  public Image getImage() {
    return new Image(
        new Pixel[][]{{new PixelRGB(0, 0, 0), new PixelRGB(0, 0, 0), new PixelRGB(0, 0, 0)},
            {new PixelRGB(0, 0, 0), new PixelRGB(0, 0, 0), new PixelRGB(0, 0, 0)},
            {new PixelRGB(0, 0, 0), new PixelRGB(0, 0, 0), new PixelRGB(0, 0, 0)}});
  }
}
