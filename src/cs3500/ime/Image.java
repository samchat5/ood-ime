package cs3500.ime;

/**
 * Concrete class representing an image. Has an array of pixels, a width, and a height. An image can
 * have all its individual pixels brightened, darkened, or be flipped vertically/horizontally.
 */
public class Image implements IImage {

  private int height;
  private int width;
  private IPixel[][] pixelArray;

  @Override
  public IImage getComponent(GreyscaleComponent component) {
    return null;
  }

  @Override
  public IImage brighten(int value) {
    return null;
  }

  @Override
  public IImage horizontalFlip() {
    return null;
  }

  @Override
  public IImage verticalFlip() {
    return null;
  }
}
