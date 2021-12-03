import static model.ImageUtil.readFile;
import static org.junit.Assert.assertEquals;

import model.Filter;
import model.FilterImpl;
import model.Image;
import model.ImageModel;
import model.Pixel;
import model.PixelRGB;
import model.RGBModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the FilterImpl class.
 */
public class FilterImplTest {

  ImageModel fourByFour;
  ImageModel fourByFourB1;
  ImageModel fourByFourB2;
  ImageModel fourByFourS1;
  ImageModel fourByFourS2;

  /**
   * Initializes values for testing. Ensures that mutation will not disrupt any tests.
   */
  @Before
  public void setUp() {
    fourByFour = new RGBModel(readFile("test/resources/4x4.png"));
    fourByFourB1 = new RGBModel(readFile("res/4by4Blur.ppm"));
    fourByFourB2 = new RGBModel(readFile("res/4by4Blur2.ppm"));
    fourByFourS1 = new RGBModel(readFile("res/4by4Sharpen.ppm"));
    fourByFourS2 = new RGBModel(readFile("res/4by4Sharpen2.ppm"));
  }

  @Test
  public void temporary() {
    Pixel[][] i = new Pixel[2][2];
    i[0][0] = new PixelRGB(100, 200, 50);
    i[0][1] = new PixelRGB(20, 200, 100);
    i[1][0] = new PixelRGB(38, 40, 100);
    i[1][1] = new PixelRGB(100, 200, 50);

    assertEquals(i[0][0], new PixelRGB(100, 200, 50));
  }

  @Test
  public void gauss() {
    this.setUp();
    fourByFour.gaussianBlur();
    assertEquals(fourByFourB1.getImage(), fourByFour.getImage());

    fourByFour.gaussianBlur();
    assertEquals(fourByFourB2.getImage(), fourByFour.getImage());

    double[][] blur = new double[3][3];
    blur[0][0] = 1f / 16;
    blur[0][1] = 1f / 8;
    blur[0][2] = 1f / 16;
    blur[1][0] = 1f / 8;
    blur[1][1] = 1f / 4;
    blur[1][2] = 1f / 8;
    blur[2][0] = 1f / 16;
    blur[2][1] = 1f / 8;
    blur[2][2] = 1f / 16;
    Filter gaussian = new FilterImpl(blur);
    Image i = gaussian.apply(fourByFour.getImage());
    assertEquals(i, fourByFourB1.getImage());

  }

  @Test
  public void sharp() {
    this.setUp();
    fourByFour.sharpen();
    assertEquals(fourByFourS1.getImage(), fourByFour.getImage());

    fourByFour.sharpen();
    assertEquals(fourByFourS2.getImage(), fourByFour.getImage());

    double[][] sharpen = {{-1f / 8, -1f / 8, -1f / 8, -1f / 8, -1f / 8},
        {-1f / 8, 0, 0, 0, -1f / 8}, {-1f / 8, 0, 1, 0, -1f / 8},
        {-1f / 8, 0, 0, 0, -1f / 8}, {-1f / 8, -1f / 8, -1f / 8, -1f / 8, -1f / 8}};
    Filter sharp = new FilterImpl(sharpen);
    Image newImage = sharp.apply(fourByFour.getImage());
    assertEquals(newImage, fourByFourS1.getImage());
  }

}