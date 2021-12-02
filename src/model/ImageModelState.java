package model;

/**
 * This interface represents operations that can be used to monitor the state of an image model,
 * without changing it.
 */
public interface ImageModelState {

  /**
   * Gets the red component of the pixel at the given location within an image.
   * @param row the row the pixel is in.
   * @param col the column the pixel is in.
   * @return an integer representing the red component.
   */
  int redValuePixel(int row, int col);

  /**
   * Gets the blue component of the pixel at the given location within an image.
   * @param row the row the pixel is in.
   * @param col the column the pixel is in.
   * @return an integer representing the blue component.
   */
  int blueValuePixel(int row, int col);

  /**
   * Gets the green component of the pixel at the given location within an image.
   * @param row the row the pixel is in.
   * @param col the column the pixel is in.
   * @return an integer representing the green component.
   */
  int greenValuePixel(int row, int col);

  /**
   * Gets the image of this RGBModel. This image is a copy of the image instead of the image itself.
   * This prevents a user from changing values in this image's array through access.
   * @return the array representing this image.
   */
  public Image getImage();

}
