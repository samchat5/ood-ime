package cs3500.ime.model.image.colortransform;

import cs3500.ime.model.image.IImage;

/**
 * Abstract class for each color transform. Checks that each kernel is the right size (3x3) and has
 * the boilerplate function shared by all subclasses for apply a transform to an {@code IImage}.
 */
public abstract class AColorTransform implements IColorTransform {

  private final double[][] kernel;

  protected AColorTransform(double[][] kernel) {
    if (kernel.length != 3 && kernel[0].length != 3) {
      throw new IllegalArgumentException("Illegal kernel size.");
    }
    this.kernel = kernel;
  }

  @Override
  public IImage applyTransform(IImage image) throws IllegalArgumentException {
    if (image == null) {
      throw new IllegalArgumentException("Null image.");
    }
    return image.applyTransform(kernel);
  }
}
