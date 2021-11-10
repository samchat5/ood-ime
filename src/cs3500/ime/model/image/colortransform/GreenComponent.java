package cs3500.ime.model.image.colortransform;

/**
 * Represents the color transform done to get the green-greyscale component of an image. Simply
 * multiplies red and blue channel values by 0, and green channels by 1.
 */
public class GreenComponent extends AColorTransform {

  public GreenComponent() {
    super(new double[][]{{0, 1, 0}, {0, 1, 0}, {0, 1, 0}});
  }
}
