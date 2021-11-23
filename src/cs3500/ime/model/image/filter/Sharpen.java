package cs3500.ime.model.image.filter;

/**
 * Represents the filter used to Sharpen an image, done by accentuating the edges of an image.
 */
public class Sharpen extends AFilter {

  /**
   * Creates a new Sharpen object by calling the super constructor.
   */
  public Sharpen() {
    super(new double[][]{{-1. / 8, -1. / 8, -1. / 8, -1. / 8, -1. / 8},
        {-1. / 8, 1. / 4, 1. / 4, 1. / 4, -1. / 8}, {-1. / 8, 1. / 4, 1, 1. / 4, -1. / 8},
        {-1. / 8, 1. / 4, 1. / 4, 1. / 4, -1. / 8}, {-1. / 8, -1. / 8, -1. / 8, -1. / 8, -1. / 8}});
  }
}
