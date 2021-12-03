package model;

/**
 * Represents a transformation that can be applied to all pixels in an image. A transformation is a
 * way of manipulating the values of a certain pixel
 */
public interface Transformation {

  /**
   * Applies the given transformation to the image. The transformation is applied based on the color
   * values of a pixel.
   *
   * @return the transformed image.
   */
  Image applyLinear(Image image);

}
