package cs3500.ime.model.image.filter;

import cs3500.ime.model.image.IImage;

/**
 * Abstract class for all filters that checks that the filter kernel is valid (i.e. has odd
 * dimensions), and provides the boilerplate method to subclasses for applying filters to image.
 */
public abstract class AFilter implements IFilter {

  private final double[][] filterKernel;

  protected AFilter(double[][] filterKernel) throws IllegalArgumentException {
    int yDimension = filterKernel.length;
    int xDimension = filterKernel[0].length;
    if ((yDimension % 2) != 1 && (xDimension % 2) != 1) {
      throw new IllegalArgumentException("Provided kernel is invalid");
    }
    this.filterKernel = filterKernel;
  }

  @Override
  public IImage applyFilter(IImage target) throws IllegalArgumentException {
    if (target == null) {
      throw new IllegalArgumentException("Null target.");
    }
    return target.applyFilter(this.filterKernel);
  }
}
