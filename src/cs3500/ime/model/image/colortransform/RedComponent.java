package cs3500.ime.model.image.colortransform;

/**
 * Represents the color transform done to get the red-greyscale component of an image. Simply
 * multiplies blue and green channel values by 0, and red channels by 1.
 */
public class RedComponent extends AColorTransform {

  public RedComponent() {
    super(new double[][]{{1, 0, 0}, {1, 0, 0}, {1, 0, 0}});
  }
}
