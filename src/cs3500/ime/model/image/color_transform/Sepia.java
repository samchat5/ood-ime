package cs3500.ime.model.image.color_transform;

/**
 * Represents a SepiaCommand color transform. Cannot be extended, and is therefore final, since all
 * sepia transformations must be the exact same.
 */
public final class Sepia extends AColorTransform {

  public Sepia() {
    super(new double[][]{{0.393, 0.769, 0.189}, {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}});
  }
}
