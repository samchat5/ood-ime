package cs3500.ime.image;

import cs3500.ime.GreyscaleComponent;
import cs3500.ime.pixel.IPixel;

/**
 * Concrete class representing an image. Has an array of pixels, a width, and a height. An image can
 * have all its individual pixels brightened, darkened, or be flipped vertically/horizontally.
 */
public class Image implements IImage {

  private final int height;
  private final int width;
  private final IPixel[][] pixelArray;

  public Image(int height, int width, IPixel[][] pixelArray) throws IllegalArgumentException {
    this.height = height;
    this.width = width;
    if (!heightWidthMatch(height, width, pixelArray)) {
      throw new IllegalArgumentException("Height/width mismatch.");
    }
    this.pixelArray = pixelArray;
  }

  private boolean heightWidthMatch(int height, int width, IPixel[][] pixelArray) {
    return pixelArray.length == height && ((pixelArray.length == 0 && width == 0) || (
        pixelArray[0].length == width));
  }

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

  @Override
  public String toString() {
    StringBuilder output = new StringBuilder();
    for (int i = 0; i < width; i++) {
      for (int j = 0; j < height; j++) {
        IPixel foo = pixelArray[i][j];
        output.append("Pixel at (").append(i).append(", ").append(j).append("):  ");
        output.append(foo.toString());
        output.append("\n");
      }
    }
    return output.toString();
  }
}
