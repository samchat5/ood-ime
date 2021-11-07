package cs3500.ime.model.image.color_transform;

public final class Luma extends AColorTransform {

  public Luma() {
    super(new double[][]{{0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722}});
  }
}
