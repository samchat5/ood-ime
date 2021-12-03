package model;

/**
 * This interface represents operations that can be used to monitor the state of an image model,
 * without changing it.
 */
public interface ImageState {

  /**
   * Acts as a getter for the image being acted upon. The image is made up of a 2d array of pixels.
   *
   * @return the two-dimensional array of pixels that make up the image
   */
  Pixel[][] getImage();

  /**
   * This method gets the width of this image. In other words, how many pixels wide the image is.
   * The width is an integer.
   *
   * @return width as an integer.
   */
  int getImageHeight();

  /**
   * This method gets the height of this image. In other words, how many pixels tall the image is.
   * The height is an integer.
   *
   * @return height as an integer.
   */
  int getImageWidth();


}
