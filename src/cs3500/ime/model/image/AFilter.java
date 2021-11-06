package cs3500.ime.model.image;

public abstract class AFilter implements IFilter {

  private final double[][] filterKernel;

  public AFilter(double[][] filterKernel) throws IllegalArgumentException {
    int xDimension = filterKernel.length;
    int yDimension = filterKernel[0].length;
    if (xDimension != yDimension && (xDimension % 2) != 1) {
      throw new IllegalArgumentException("Provided kernel is invalid");
    }
    this.filterKernel = filterKernel;
  }

  @Override
  public IImage applyFilter(IImage target) {
    return target.applyFilter(this.filterKernel);
  }
}
