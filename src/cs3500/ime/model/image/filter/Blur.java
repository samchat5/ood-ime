package cs3500.ime.model.image.filter;

public class Blur extends AFilter {

  public Blur() throws IllegalArgumentException {
    super(new double[][]{{1. / 16, 1. / 8, 1. / 16}, {1. / 8, 1. / 4, 1. / 8},
        {1. / 16, 1. / 8, 1. / 16}});
  }
}
