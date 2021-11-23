package cs3500.ime.model.image.colortransform;

import cs3500.ime.model.image.IImage;

/**
 * Represents the interface for a color transform. Contains one method that takes in an image, does
 * a transform on it, and returns a new {@code IImage} object with the applied transformation.
 */
public interface IColorTransform {

  /**
   * Applies this color transformation to the given image.
   *
   * @param image image to apply transformation on
   * @return transformed image
   * @throws IllegalArgumentException if image is null
   */
  IImage applyTransform(IImage image) throws IllegalArgumentException;
}
