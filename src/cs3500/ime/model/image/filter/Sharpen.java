package cs3500.ime.model.image.filter;

public class Sharpen extends AFilter {

  public Sharpen() throws IllegalArgumentException {
    super(new double[][]{{-1. / 8, -1. / 8, -1. / 8, -1. / 8, -1. / 8},
        {-1. / 8, 1. / 4, 1. / 4, 1. / 4, -1. / 8}, {-1. / 8, 1. / 4, 1, 1. / 4, -1. / 8},
        {-1. / 8, 1. / 4, 1. / 4, 1. / 4, -1. / 8}, {-1. / 8, -1. / 8, -1. / 8, -1. / 8, -1. / 8}});
  }
}
