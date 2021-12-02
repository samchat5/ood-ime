import org.junit.Before;
import org.junit.Test;

import model.Image;
import model.ImageModel;
import model.RGBModel;
import model.Transformation;
import model.TransformationImpl;

import static model.ImageUtil.readFile;
import static org.junit.Assert.assertEquals;

/**
 * Tests for transformationImpl class.
 */
public class TransformationImplTest {

  ImageModel oneBlack;
  ImageModel oneBlackCopy;
  ImageModel hedgehog;
  ImageModel fourByFour;
  ImageModel fourByFourB1;
  ImageModel fourByFourB2;
  ImageModel fourByFourS1;
  ImageModel fourByFourS2;
  ImageModel fourByFourGrey;
  ImageModel fourByFourSepia;

  /**
   * Initializes values for testing.
   * Ensures that mutation will not disrupt any tests.
   */
  @Before
  public void setUp() {
    fourByFour = new RGBModel(readFile("res/4by4.ppm"));
    fourByFourB1 = new RGBModel( readFile("res/4by4Blur.ppm"));
    fourByFourB2 = new RGBModel(readFile("res/4by4Blur2.ppm"));
    fourByFourS1 = new RGBModel(readFile("res/4by4Sharpen.ppm"));
    fourByFourS2 = new RGBModel(readFile("res/4by4Sharpen2.ppm"));
    fourByFourGrey = new RGBModel(readFile("res/4by4Greyscale.ppm"));
    fourByFourSepia = new RGBModel(readFile("res/4by4Sepia.ppm"));
    oneBlack = new RGBModel(readFile("res/one_black_pixel.ppm"));
    oneBlackCopy = new RGBModel(readFile("res/one_black_pixel.ppm"));
    hedgehog = new RGBModel(readFile("res/hedg_grayscale.ppm"));
  }

  @Test
  public void grey() {
    this.setUp();
    fourByFour.greyscale();
    assertEquals(fourByFourGrey, fourByFour);

    oneBlack.greyscale();
    assertEquals(oneBlack, oneBlackCopy);

    double[][] grey = new double[3][3];
    grey[0][0] = .2126;
    grey[0][1] = .7152;
    grey[0][2] = .2126;
    grey[1][0] = .2126;
    grey[1][1] = .7152;
    grey[1][2] = .2126;
    grey[2][0] = .2126;
    grey[2][1] = .7152;
    grey[2][2] = .2126;
    Transformation greyscale = new TransformationImpl(grey);
    Image i = greyscale.applyLinear(fourByFour.getImage());
    assertEquals(i, fourByFourGrey.getImage());
  }

  @Test
  public void sepia() {
    this.setUp();
    fourByFour.sepia();
    assertEquals(fourByFourSepia, fourByFour);

    oneBlack.sepia();
    assertEquals(oneBlack, oneBlackCopy);

    double[][] sep = new double[3][3];
    sep[0][0] = .393;
    sep[0][1] = .769;
    sep[0][2] = .189;
    sep[1][0] = .349;
    sep[1][1] = .686;
    sep[1][2] = .168;
    sep[2][0] = .272;
    sep[2][1] = .534;
    sep[2][2] = .131;
    Transformation sepia = new TransformationImpl(sep);
    Image i = sepia.applyLinear(fourByFour.getImage());
    assertEquals(i, fourByFourSepia);
  }
}