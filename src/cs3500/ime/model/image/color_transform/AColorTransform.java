package cs3500.ime.model.image.color_transform;

import cs3500.ime.model.image.IImage;

public abstract class AColorTransform implements IColorTransform {

  private final double[][] kernel;

  public AColorTransform(double[][] kernel) {
    if (kernel.length != 3 && kernel[0].length != 3) {
      throw new IllegalArgumentException("Illegal kernel size.");
    }
    this.kernel = kernel;
  }

  @Override
  public IImage applyTransform(IImage image) {
    return image.applyTransform(kernel);
  }
}
