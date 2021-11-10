package cs3500.ime.model.image.filter;

/**
 * Represents the filter used to do a weak Gaussian blur over an image.
 */
public class Blur extends AFilter {

  public Blur() {
    super(new double[][]{{1. / 16, 1. / 8, 1. / 16}, {1. / 8, 1. / 4, 1. / 8},
        {1. / 16, 1. / 8, 1. / 16}});
  }
}
