package cs3500.ime.model.image.colortransform;

/**
 * Represents the transform to get the sepia-color version of an image. A Sepia image is an image
 * which has the reddish-brown pigmentation of old photographs.
 */
public class Sepia extends AColorTransform {

  public Sepia() {
    super(new double[][]{{0.393, 0.769, 0.189}, {0.349, 0.686, 0.168}, {0.272, 0.534, 0.131}});
  }
}
