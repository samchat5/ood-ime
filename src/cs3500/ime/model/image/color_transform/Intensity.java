package cs3500.ime.model.image.color_transform;

public final class Intensity extends AColorTransform {

  public Intensity() {
    super(new double[][]{{1. / 3, 1. / 3, 1. / 3}, {1. / 3, 1. / 3, 1. / 3},
        {1. / 3, 1. / 3, 1. / 3},});
  }
}
