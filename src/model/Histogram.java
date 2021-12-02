package model;

/**
 * Represents the Histogram interface. Contains the methods pertaining to constructing histograms
 * for a variety of representations.
 */
public interface Histogram {

  /**
   * Constructs the histogram for the red component of the given image.
   * The histogram is represents by an array of values for
   * @param i is the image that the histogram is based off of.
   * @return the integer array that contains the frequency for each potential value.
   */
  public int[] redHistogram(Image i);

  /**
   * Constructs the histogram for the green component of the given image.
   * The histogram is represents by an array of values for
   * @param i is the image that the histogram is based off of.
   * @return the integer array that contains the frequency for each potential value.
   */
  public int[] greenHistogram(Image i);

  /**
   * Constructs the histogram for the blue component of the given image.
   * The histogram is represents by an array of values for
   * @param i is the image that the histogram is based off of.
   * @return the integer array that contains the frequency for each potential value.
   */
  public int[] blueHistogram(Image i);

  /**
   * Constructs the histogram for the intensity component of the given image.
   * The histogram is represents by an array of values for
   * @param i is the image that the histogram is based off of.
   * @return the integer array that contains the frequency for each potential value.
   */
  public int[] intensityHistogram(Image i);

}
