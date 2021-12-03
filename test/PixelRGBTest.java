import static org.junit.Assert.assertEquals;

import model.PixelRGB;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents the tests for the PixelRGB class. Ensures that all methods and constructors are
 * working as they are supposed to as defined by their descriptions.
 */
public class PixelRGBTest {

  PixelRGB all0;
  PixelRGB all255;
  PixelRGB mixedRed;
  PixelRGB mixedBlue;
  PixelRGB mixedGreen;

  /**
   * Initializes values for testing. Ensures that mutation will not disrupt any tests.
   */
  @Before
  public void setUp() {
    all0 = new PixelRGB(0, 0, 0);
    all255 = new PixelRGB(255, 255, 255);
    mixedRed = new PixelRGB(10, 5, 1);
    mixedBlue = new PixelRGB(0, 200, 150);
    mixedGreen = new PixelRGB(100, 200, 255);
  }


  @Test
  public void correctConstructor() {
    this.setUp();
    assertEquals(all0.getGreen(), 0);
    assertEquals(all0.getRed(), 0);
    assertEquals(all0.getBlue(), 0);

    assertEquals(all255.getGreen(), 255);
    assertEquals(all255.getRed(), 255);
    assertEquals(all255.getBlue(), 255);

    assertEquals(mixedBlue.getGreen(), 150);
    assertEquals(mixedBlue.getRed(), 0);
    assertEquals(mixedBlue.getBlue(), 200);

    assertEquals(mixedRed.getGreen(), 1);
    assertEquals(mixedRed.getRed(), 10);
    assertEquals(mixedRed.getBlue(), 5);

    assertEquals(mixedGreen.getGreen(), 255);
    assertEquals(mixedGreen.getRed(), 100);
    assertEquals(mixedGreen.getBlue(), 200);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructFail() {
    new PixelRGB(-1, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructFail2() {
    new PixelRGB(0, -10, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructFail3() {
    new PixelRGB(0, 0, -255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructFail4() {
    new PixelRGB(260, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructFail5() {
    new PixelRGB(10, 270, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructFail6() {
    new PixelRGB(20, 0, 256);
  }

  @Test
  public void computeValueTest() {
    this.setUp();
    assertEquals(all0.computeValue(), 0);
    assertEquals(all255.computeValue(), 255);
    assertEquals(mixedGreen.computeValue(), 255);
    assertEquals(mixedBlue.computeValue(), 200);
    assertEquals(mixedRed.computeValue(), 10);
  }

  @Test
  public void computeIntensityTest() {
    this.setUp();
    assertEquals(all0.computeIntensity(), 0);
    assertEquals(all255.computeIntensity(), 255);
    assertEquals(mixedGreen.computeIntensity(), 185);
    assertEquals(mixedBlue.computeIntensity(), 116);
    assertEquals(mixedRed.computeIntensity(), 5);
  }

  @Test
  public void computeLumaTest() {
    this.setUp();
    assertEquals(all0.computeLuma(), 0);
    assertEquals(all255.computeLuma(), 255);
    assertEquals(mixedGreen.computeLuma(), 218);
    assertEquals(mixedBlue.computeLuma(), 122);
    assertEquals(mixedRed.computeLuma(), 3);
  }


  @Test
  public void makePixelRedTest() {
    this.setUp();
    all0.makePixelRed();
    assertEquals(all0.getBlue(), 0);
    assertEquals(all0.getGreen(), 0);
    assertEquals(all0.getRed(), 0);
    all255.makePixelRed();
    assertEquals(all255.getBlue(), 255);
    assertEquals(all255.getGreen(), 255);
    assertEquals(all255.getRed(), 255);
    mixedGreen.makePixelRed();
    assertEquals(mixedGreen.getBlue(), 100);
    assertEquals(mixedGreen.getGreen(), 100);
    assertEquals(mixedGreen.getRed(), 100);
    mixedRed.makePixelRed();
    assertEquals(mixedRed.getBlue(), 10);
    assertEquals(mixedRed.getGreen(), 10);
    assertEquals(mixedRed.getRed(), 10);
    mixedBlue.makePixelRed();
    assertEquals(mixedBlue.getBlue(), 0);
    assertEquals(mixedBlue.getGreen(), 0);
    assertEquals(mixedBlue.getRed(), 0);
  }

  @Test
  public void makePixelBlueTest() {
    this.setUp();
    all0.makePixelBlue();
    assertEquals(all0.getBlue(), 0);
    assertEquals(all0.getGreen(), 0);
    assertEquals(all0.getRed(), 0);
    all255.makePixelBlue();
    assertEquals(all255.getBlue(), 255);
    assertEquals(all255.getGreen(), 255);
    assertEquals(all255.getRed(), 255);
    mixedGreen.makePixelBlue();
    assertEquals(mixedGreen.getBlue(), 200);
    assertEquals(mixedGreen.getGreen(), 200);
    assertEquals(mixedGreen.getRed(), 200);
    mixedRed.makePixelBlue();
    assertEquals(mixedRed.getBlue(), 5);
    assertEquals(mixedRed.getGreen(), 5);
    assertEquals(mixedRed.getRed(), 5);
    mixedBlue.makePixelBlue();
    assertEquals(mixedBlue.getBlue(), 200);
    assertEquals(mixedBlue.getGreen(), 200);
    assertEquals(mixedBlue.getRed(), 200);
  }

  @Test
  public void makePixelGreenTest() {
    this.setUp();
    all0.makePixelGreen();
    assertEquals(all0.getBlue(), 0);
    assertEquals(all0.getGreen(), 0);
    assertEquals(all0.getRed(), 0);
    all255.makePixelGreen();
    assertEquals(all255.getBlue(), 255);
    assertEquals(all255.getGreen(), 255);
    assertEquals(all255.getRed(), 255);
    mixedGreen.makePixelGreen();
    assertEquals(mixedGreen.getBlue(), 255);
    assertEquals(mixedGreen.getGreen(), 255);
    assertEquals(mixedGreen.getRed(), 255);
    mixedRed.makePixelGreen();
    assertEquals(mixedRed.getBlue(), 1);
    assertEquals(mixedRed.getGreen(), 1);
    assertEquals(mixedRed.getRed(), 1);
    mixedBlue.makePixelGreen();
    assertEquals(mixedBlue.getBlue(), 150);
    assertEquals(mixedBlue.getGreen(), 150);
    assertEquals(mixedBlue.getRed(), 150);
  }


  @Test
  public void makePixelBrighterTest() {
    this.setUp();
    all0.makePixelBrighter(255);
    assertEquals(all0.getBlue(), 255);
    assertEquals(all0.getGreen(), 255);
    assertEquals(all0.getRed(), 255);

    all255.makePixelBrighter(1);
    assertEquals(all255.getBlue(), 255);
    assertEquals(all255.getGreen(), 255);
    assertEquals(all255.getRed(), 255);

    mixedGreen.makePixelBrighter(100);
    assertEquals(mixedGreen.getBlue(), 255);
    assertEquals(mixedGreen.getGreen(), 255);
    assertEquals(mixedGreen.getRed(), 200);

    mixedBlue.makePixelBrighter(100);
    assertEquals(mixedBlue.getBlue(), 255);
    assertEquals(mixedBlue.getGreen(), 250);
    assertEquals(mixedBlue.getRed(), 100);

    mixedRed.makePixelBrighter(50);
    assertEquals(mixedRed.getBlue(), 55);
    assertEquals(mixedRed.getGreen(), 51);
    assertEquals(mixedRed.getRed(), 60);

    mixedRed.makePixelBrighter(200);
    assertEquals(mixedRed.getBlue(), 255);
    assertEquals(mixedRed.getGreen(), 251);
    assertEquals(mixedRed.getRed(), 255);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeBrighter() {
    all0.makePixelBrighter(-10);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negativeDarker() {
    all0.makePixelBrighter(-10);
  }

  @Test
  public void makePixelDarkerTest() {
    this.setUp();
    all0.makePixelDarker(255);
    assertEquals(all0.getBlue(), 0);
    assertEquals(all0.getGreen(), 0);
    assertEquals(all0.getRed(), 0);

    all255.makePixelDarker(1);
    assertEquals(all255.getBlue(), 254);
    assertEquals(all255.getGreen(), 254);
    assertEquals(all255.getRed(), 254);

    mixedGreen.makePixelDarker(100);
    assertEquals(mixedGreen.getBlue(), 100);
    assertEquals(mixedGreen.getGreen(), 155);
    assertEquals(mixedGreen.getRed(), 0);

    mixedBlue.makePixelDarker(100);
    assertEquals(mixedBlue.getBlue(), 100);
    assertEquals(mixedBlue.getGreen(), 50);
    assertEquals(mixedBlue.getRed(), 0);

    mixedRed.makePixelDarker(50);
    assertEquals(mixedRed.getBlue(), 0);
    assertEquals(mixedRed.getGreen(), 0);
    assertEquals(mixedRed.getRed(), 0);

    mixedRed.makePixelDarker(200);
    assertEquals(mixedRed.getBlue(), 0);
    assertEquals(mixedRed.getGreen(), 0);
    assertEquals(mixedRed.getRed(), 0);
  }

  @Test
  public void makePixelValueTest() {
    this.setUp();
    all0.makePixelValue();
    assertEquals(all0.getBlue(), 0);
    assertEquals(all0.getGreen(), 0);
    assertEquals(all0.getRed(), 0);

    all255.makePixelValue();
    assertEquals(all255.getBlue(), 255);
    assertEquals(all255.getGreen(), 255);
    assertEquals(all255.getRed(), 255);

    mixedGreen.makePixelValue();
    assertEquals(mixedGreen.getBlue(), 255);
    assertEquals(mixedGreen.getGreen(), 255);
    assertEquals(mixedGreen.getRed(), 255);

    mixedBlue.makePixelValue();
    assertEquals(mixedBlue.getBlue(), 200);
    assertEquals(mixedBlue.getGreen(), 200);
    assertEquals(mixedBlue.getRed(), 200);

    mixedRed.makePixelValue();
    assertEquals(mixedRed.getBlue(), 10);
    assertEquals(mixedRed.getGreen(), 10);
    assertEquals(mixedRed.getRed(), 10);
  }

  @Test
  public void makePixelIntensityTest() {
    this.setUp();
    all0.makePixelIntensity();
    assertEquals(all0.getBlue(), 0);
    assertEquals(all0.getGreen(), 0);
    assertEquals(all0.getRed(), 0);

    all255.makePixelIntensity();
    assertEquals(all255.getBlue(), 255);
    assertEquals(all255.getGreen(), 255);
    assertEquals(all255.getRed(), 255);

    mixedGreen.makePixelIntensity();
    assertEquals(mixedGreen.getBlue(), 185);
    assertEquals(mixedGreen.getGreen(), 185);
    assertEquals(mixedGreen.getRed(), 185);

    mixedBlue.makePixelIntensity();
    assertEquals(mixedBlue.getBlue(), 116);
    assertEquals(mixedBlue.getGreen(), 116);
    assertEquals(mixedBlue.getRed(), 116);

    mixedRed.makePixelIntensity();
    assertEquals(mixedRed.getBlue(), 5);
    assertEquals(mixedRed.getGreen(), 5);
    assertEquals(mixedRed.getRed(), 5);
  }

  @Test
  public void makePixelLumaTest() {
    this.setUp();
    all0.makePixelLuma();
    assertEquals(all0.getBlue(), 0);
    assertEquals(all0.getGreen(), 0);
    assertEquals(all0.getRed(), 0);

    all255.makePixelLuma();
    assertEquals(all255.getBlue(), 255);
    assertEquals(all255.getGreen(), 255);
    assertEquals(all255.getRed(), 255);

    mixedGreen.makePixelLuma();
    assertEquals(mixedGreen.getBlue(), 218);
    assertEquals(mixedGreen.getGreen(), 218);
    assertEquals(mixedGreen.getRed(), 218);

    mixedBlue.makePixelLuma();
    assertEquals(mixedBlue.getBlue(), 122);
    assertEquals(mixedBlue.getGreen(), 122);
    assertEquals(mixedBlue.getRed(), 122);

    mixedRed.makePixelLuma();
    assertEquals(mixedRed.getBlue(), 3);
    assertEquals(mixedRed.getGreen(), 3);
    assertEquals(mixedRed.getRed(), 3);
  }

  @Test
  public void getRedTest() {
    this.setUp();
    assertEquals(0, all0.getRed());
    assertEquals(255, all255.getRed());
    assertEquals(10, mixedRed.getRed());
    assertEquals(0, mixedBlue.getRed());
  }

  @Test
  public void getGreenTest() {
    this.setUp();
    assertEquals(0, all0.getGreen());
    assertEquals(255, all255.getGreen());
    assertEquals(1, mixedRed.getGreen());
    assertEquals(150, mixedBlue.getGreen());
  }

  @Test
  public void getBlueTest() {
    this.setUp();
    assertEquals(0, all0.getBlue());
    assertEquals(255, all255.getBlue());
    assertEquals(5, mixedRed.getBlue());
    assertEquals(255, mixedBlue.getBlue());
  }
}