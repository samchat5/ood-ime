import static model.ImageUtil.readFile;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import model.Image;
import model.Pixel;
import model.PixelRGB;
import org.junit.Before;
import org.junit.Test;

/**
 * Contains the tests for the methods in the Image class. Tests that the various methods and
 * constructor work properly.
 */
public class ImageTest {

  Image iflower;
  Image iflower2;
  Image iPixel;
  Image by4;
  Pixel[][] pixels;
  PixelRGB one;
  Pixel[][] pix;

  /**
   * Initializes variables used for testing. Ensures that mutation does not cause testing problems.
   */
  @Before
  public void setUp() {
    iflower = readFile("res/flower.ppm");
    iflower2 = readFile("res/flower.ppm");
    iPixel = readFile("res/one_black_pixel.ppm");
    by4 = readFile("res/4by4.ppm");

    pixels = new Pixel[4][4];
    pixels[0][0] = new PixelRGB(0, 0, 0);
    pixels[0][1] = new PixelRGB(0, 0, 0);
    pixels[0][2] = new PixelRGB(0, 0, 0);
    pixels[0][3] = new PixelRGB(15, 0, 15);

    pixels[1][0] = new PixelRGB(0, 0, 0);
    pixels[1][1] = new PixelRGB(0, 15, 7);
    pixels[1][2] = new PixelRGB(0, 0, 0);
    pixels[1][3] = new PixelRGB(0, 0, 0);

    pixels[2][0] = new PixelRGB(0, 0, 0);
    pixels[2][1] = new PixelRGB(0, 0, 0);
    pixels[2][2] = new PixelRGB(0, 15, 7);
    pixels[2][3] = new PixelRGB(0, 0, 0);

    pixels[3][0] = new PixelRGB(15, 0, 15);
    pixels[3][1] = new PixelRGB(0, 0, 0);
    pixels[3][2] = new PixelRGB(0, 0, 0);
    pixels[3][3] = new PixelRGB(0, 0, 0);

    one = new PixelRGB(0, 0, 0);
    pix = new Pixel[1][1];
    pix[0][0] = one;
  }

  @Test
  public void construct() {
    this.setUp();
    assertEquals(iflower.getImageHeight(), 7);
    assertEquals(iPixel.getImageHeight(), 1);
    assertEquals(by4.getImageHeight(), 4);
    assertEquals(by4.getImageWidth(), 4);
    assertEquals(iflower.getImageWidth(), 10);
    assertEquals(iPixel.getImageWidth(), 1);
    assertEquals(iflower, readFile("res/flower.ppm"));
    assertEquals(iPixel, readFile("res/one_black_pixel.ppm"));
    assertArrayEquals(iPixel.getImage(), pix);
    assertArrayEquals(by4.getImage(), pixels);
  }

  @Test
  public void getImageTest() {
    assertArrayEquals(by4.getImage(), pixels);
    assertArrayEquals(iPixel.getImage(), pix);
  }

  @Test
  public void getImageHeightTest() {
    assertEquals(iflower.getImageHeight(), 7);
    assertEquals(iPixel.getImageHeight(), 1);
  }

  @Test
  public void getImageWidthTest() {
    assertEquals(iflower.getImageWidth(), 10);
    assertEquals(iPixel.getImageWidth(), 1);
  }

  @Test
  public void equalsTest() {
    assertEquals(iflower, iflower2);
    assertNotEquals(iflower, iPixel);
  }

  @Test
  public void hashCodeTest() {
    assertEquals(iflower.hashCode(), iflower2.hashCode());
    assertNotEquals(iflower.hashCode(), by4.hashCode());
  }
}