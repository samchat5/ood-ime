package model;

/**
 * Represents an implementation of the Histogram interface for RGBModels. Values for histogram based
 * off of the rbg color values.
 */
public class HistogramImpl implements Histogram {

  /**
   * Returns the histogram of the red channel of the image.
   *
   * @param i the image to be analyzed
   * @return int array representing the histogram of the red channel
   */
  public int[] redHistogram(Image i) {
    RGBModel m = new RGBModel(i);
    m.makeRed();
    return generateHistogram(m.getImage());
  }

  /**
   * Returns the histogram of the green channel of the image.
   *
   * @param i the image to be analyzed
   * @return int array representing the histogram of the green channel
   */
  public int[] greenHistogram(Image i) {
    RGBModel m = new RGBModel(i);
    m.makeGreen();
    return generateHistogram(m.getImage());
  }

  /**
   * Returns the histogram of the blue channel of the image.
   *
   * @param i the image to be analyzed
   * @return int array representing the histogram of the blue channel
   */
  public int[] blueHistogram(Image i) {
    RGBModel m = new RGBModel(i);
    m.makeBlue();
    return generateHistogram(m.getImage());
  }

  /**
   * Returns the intensity histogram of the image.
   *
   * @param i the image to be analyzed
   * @return int array representing the histogram of the intensity
   */
  public int[] intensityHistogram(Image i) {
    RGBModel m = new RGBModel(i);
    m.imageIntensity();
    return generateHistogram(m.getImage());
  }

  private int[] generateHistogram(Image i) {
    int[] histogram = new int[256];
    for (int row = 0; row < i.getImageHeight(); row++) {
      for (int col = 0; col < i.getImageWidth(); col++) {
        int num = i.getImage()[row][col].getRed();
        int getVal = histogram[num];
        getVal += 1;
        histogram[num] = getVal;
      }
    }
    return histogram;
  }
}
