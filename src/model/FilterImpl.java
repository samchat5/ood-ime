package model;

/**
 * Represents the methods associated with filtering images using
 * given parameters for the filter.
 * The filter is based on a 2D matrix of values.
 * Implements the filter interface.
 */
public class FilterImpl implements Filter {

  private double[][] filter;

  /**
   * Represents a filter that can be applied to an image.
   * The filter is represented by a 2D matrix that must have odd dimensions.
   * @param filter represents the 2d matrix of values of the filter being represented.
   */
  public FilterImpl(double[][] filter) {
    if (filter.length % 2 == 0) {
      throw new IllegalArgumentException("filter must have odd dimensions");
    }

    int size = filter[0].length;

    for (int r = 0; r < size; r++) {
      if (size != filter[r].length) {
        throw new IllegalArgumentException("filter must be a rectangle");
      }
      if (filter[r].length % 2 == 0) {
        throw new IllegalArgumentException("filter must have odd dimensions");
      }
    }

    this.filter = filter;
  }

  /**
   * Applies a given kernel based filter to an image.
   * Produces an updated image with all pixels updated.
   * @param image represents the image the filter is being applied to.
   * @return the updated image after the filter has been applied.
   */
  @Override
  public Image apply(Image image) {

    int width = image.getImageWidth();
    int height = image.getImageHeight();

    Pixel[][] newImage = new Pixel[height][width];

    for (int r = 0; r < height; r++) {
      for (int c = 0; c < width; c++) {
        int[][][] kernel = getKernel(image.getImage(), r, c);
        newImage[r][c] = pixelApply(kernel);
      }
    }

    return new Image(newImage);
  }

  private PixelRGB pixelApply(int[][][] kernel) {
    int red = 0;
    int green = 0;
    int blue = 0;
    for (int i = 0; i < this.getHeight(); i++) {
      for (int j = 0; j < this.getWidth(); j++) {
        red += (int) Math.round((kernel[i][j][0] * this.filter[i][j]));
        green += (int) Math.round((kernel[i][j][1] * this.filter[i][j]));
        blue += (int) Math.round((kernel[i][j][2] * this.filter[i][j]));
      }
    }
    red = this.range(red);
    green = this.range(green);
    blue = this.range(blue);
    return new PixelRGB(red, blue, green);
  }

  private int[][][] getKernel(Pixel[][] image, int row, int col) {
    int halfR = (int) Math.floor(this.getHeight() / 2);
    int halfC = (int) Math.floor(this.getWidth() / 2);
    int[][][] kernel = new int[this.getHeight()][this.getWidth()][3];

    for (int r = 0; r < this.getHeight(); r++) {
      for (int c = 0; c < this.getWidth(); c++) {
        if ((r + (row - halfR) > 0 && r + (row - halfR) < image.length)
                && (c + (col - halfC) > 0 && c + (col - halfC) < image[0].length)) {
          kernel[r][c][0] = image[r + (row - halfR)][c + (col - halfC)].getRed();
          kernel[r][c][1] = image[r + (row - halfR)][c + (col - halfC)].getGreen();
          kernel[r][c][2] = image[r + (row - halfR)][c + (col - halfC)].getBlue();
        }
        else {
          kernel[r][c][0] = 0;
          kernel[r][c][1] = 0;
          kernel[r][c][2] = 0;
        }
      }
    }

    return kernel;
  }

  private int range(int value) {
    value = Math.min(value, 255);
    value = Math.max(value, 0);
    return value;
  }

  private int getHeight() {
    return this.filter.length;
  }

  private int getWidth() {
    return this.filter[0].length;
  }

}
