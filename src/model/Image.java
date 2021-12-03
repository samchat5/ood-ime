package model;

import java.util.Arrays;

/**
 * This class represents the implementation of ImageState (Image interface). It is used to store the
 * pixels of a given image. This class defines all the methods that can be used to monitor the state
 * of an image without changing it.
 */
public class Image implements ImageState {

  //A image is a 2D array of pixels.
  private final Pixel[][] image;
  private final int width;

  private final int height;


  /**
   * This constructor creates an image given a 2D array of pixels. It computes the width and height
   * of the image using the getImageWidth() and getImageHeight() methods.
   *
   * @param image a 2D array of pixels representing an image.
   */
  public Image(Pixel[][] image) {
    if (image == null) {
      throw new IllegalArgumentException("Cannot be null");
    } else {
      this.image = image;
      this.width = this.image[0].length;
      this.height = this.image.length;
    }
  }


  /**
   * Gets the image at its current state. The image is an array of pixels.
   *
   * @return the array of pixels that make up the image.
   */
  @Override
  public Pixel[][] getImage() {
    return this.image;
  }

  /**
   * Gets the height of the image. Height is represented by number of pixels.
   *
   * @return the height of the image in pixels.
   */
  public int getImageHeight() {
    return this.height;
  }

  /**
   * Gets the width of the image. Width is represented by number of pixels.
   *
   * @return the width of the image in pixels.
   */
  public int getImageWidth() {
    return this.width;
  }

  /**
   * Computes what the maximum value is across all pixels in the image. The value is the maximum
   * number in the color representation.
   *
   * @return the maximum value.
   */
  public int getMaxRgb() {
    int max = image[0][0].computeValue();  //first value
    for (int row = 0; row < getImageHeight(); row++) {
      for (int col = 0; col < getImageWidth(); col++) {
        if (image[row][col].computeValue() > max) {
          max = image[row][col].computeValue();  //new maximum
        }
      }
    }
    return max;
  }


  /**
   * This method overrides Java's built-in equals method. An image is equal to another image if the
   * given image is an Image and
   *
   * @param image an Image that represents the pixels of an image.
   * @return a boolean; true when the images are equal, otherwise returns false.
   */
  @Override
  public boolean equals(Object image) {
    if (!(image instanceof Image)) {
      return false;
    }

    Image givenImage = (Image) image;

    for (int row = 0; row < getImageHeight(); row++) {
      for (int col = 0; col < getImageWidth(); col++) {
        Pixel givenPixel = givenImage.image[row][col];
        Pixel thisPixel = this.image[row][col];
        if (!(thisPixel.equals(givenPixel))) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Overrides hashcode using the same fields equals uses.
   *
   * @return an integer representing the hashcode of this image.
   */
  @Override
  public int hashCode() {
    return Arrays.deepHashCode(image);
  }

}
