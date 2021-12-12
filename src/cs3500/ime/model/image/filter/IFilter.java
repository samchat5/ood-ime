package cs3500.ime.model.image.filter;

import cs3500.ime.model.image.IImage;

/**
 * This interface represents an image filter.
 */
public interface IFilter {

  /**
   * Applies this filter to the target image.
   *
   * @param target original image that is being filtered
   * @return a new IImage with this filter applied to every pixel and component of target
   * @throws IllegalArgumentException if the target is null
   */
  IImage applyFilter(IImage target) throws IllegalArgumentException;


  /**
   * Applies this filter to the target image, using the given mask.
   *
   * @param target original image that is being filtered
   * @param mask   mask to be used for this filter
   * @return a new IImage with this filter applied to every pixel and component of target
   * @throws IllegalArgumentException if the target is null or mask is invalid
   */
  IImage applyFilter(IImage target, boolean[][] mask) throws IllegalArgumentException;
}
