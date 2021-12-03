package model;

/**
 * Represents a filter. A filter is a transformation that can be applied to an image;
 */
public interface Filter {

  /**
   * Applies a filter to an image. Produces an updated image with all pixels updated.
   *
   * @param image represents the image the filter is being applied to.
   * @return the updated image after the filter has been applied.
   */
  Image apply(Image image);

}
