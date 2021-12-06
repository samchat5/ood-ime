import static model.ImageUtil.readFile;
import static org.junit.Assert.assertEquals;

import model.HistogramImpl;
import model.ImageModel;
import model.RGBModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents the tests for the histogram impl class. Ensures that proper histograms are created
 * when using the methods on various images.
 */
public class HistogramImplTest {

  ImageModel fourByFour;
  ImageModel oneBlack;
  int[] histogram;

  @Before
  public void setUp() {
    fourByFour = new RGBModel(readFile("res/4by4.ppm"));
    oneBlack = new RGBModel(readFile("res/one_black_pixel.ppm"));
    histogram = new int[256];
  }

  @Test
  public void redHistogramTest() {
    this.setUp();
    HistogramImpl h = new HistogramImpl();
    int[] red = h.redHistogram(oneBlack.getImage());
    histogram[0] = 1;
    for (int i = 0; i < 256; i++) {
      assertEquals(histogram[i], red[i]);
    }

    this.setUp();
    int[] red2 = h.redHistogram(fourByFour.getImage());
    histogram[2] = 3;
    histogram[1] = 4;
    histogram[4] = 2;
    histogram[0] = 3;
    histogram[10] = 1;
    histogram[8] = 3;
    for (int i = 0; i < 256; i++) {
      assertEquals(histogram[i], red2[i]);
    }
  }

  @Test
  public void greenHistogramTest() {
    this.setUp();
    HistogramImpl h = new HistogramImpl();
    int[] green = h.greenHistogram(oneBlack.getImage());
    histogram[0] = 1;
    for (int i = 0; i < 256; i++) {
      assertEquals(histogram[i], green[i]);
    }

    this.setUp();
    int[] green2 = h.greenHistogram(fourByFour.getImage());
    histogram[2] = 3;
    histogram[1] = 4;
    histogram[4] = 2;
    histogram[0] = 3;
    histogram[10] = 1;
    histogram[8] = 3;
    for (int i = 0; i < 256; i++) {
      assertEquals(histogram[i], green2[i]);
    }
  }

  @Test
  public void blueHistogramTest() {
    this.setUp();
    HistogramImpl h = new HistogramImpl();
    int[] blue = h.blueHistogram(oneBlack.getImage());
    histogram[0] = 1;
    for (int i = 0; i < 256; i++) {
      assertEquals(histogram[i], blue[i]);
    }

    this.setUp();
    int[] blue2 = h.blueHistogram(fourByFour.getImage());
    histogram[2] = 3;
    histogram[1] = 4;
    histogram[4] = 2;
    histogram[0] = 3;
    histogram[10] = 1;
    histogram[8] = 3;
    for (int i = 0; i < 256; i++) {
      assertEquals(histogram[i], blue2[i]);
    }
  }

  @Test
  public void intensityHistogramTest() {
    this.setUp();
    HistogramImpl h = new HistogramImpl();
    int[] in = h.intensityHistogram(oneBlack.getImage());
    histogram[0] = 1;
    for (int i = 0; i < 256; i++) {
      assertEquals(histogram[i], in[i]);
    }

    this.setUp();
    int[] in2 = h.intensityHistogram(fourByFour.getImage());
    histogram[2] = 3;
    histogram[1] = 4;
    histogram[4] = 2;
    histogram[0] = 3;
    histogram[10] = 1;
    histogram[8] = 3;
    for (int i = 0; i < 256; i++) {
      assertEquals(histogram[i], in2[i]);
    }
  }
}