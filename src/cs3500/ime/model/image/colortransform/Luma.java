package cs3500.ime.model.image.colortransform;

/**
 * Represents the transform to get the luma-greyscale component of an image. This is an alternative
 * way of getting the Luma component of an image (see the `luma-greyscale` command in the
 * controller).
 */
public class Luma extends AColorTransform {

  public Luma() {
    super(new double[][]{{0.2126, 0.7152, 0.0722}, {0.2126, 0.7152, 0.0722},
        {0.2126, 0.7152, 0.0722}});
  }
}
