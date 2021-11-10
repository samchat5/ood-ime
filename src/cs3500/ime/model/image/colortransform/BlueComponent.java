package cs3500.ime.model.image.colortransform;

/**
 * Represents the color transform done to get the blue-greyscale component of an image. Simply
 * multiplies red and green channel values by 0, and blue channels by 1.
 */
public class BlueComponent extends AColorTransform {

  public BlueComponent() {
    super(new double[][]{{0, 0, 1}, {0, 0, 1}, {0, 0, 1}});
  }
}
