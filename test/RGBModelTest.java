import static model.ImageUtil.readFile;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;

import model.Image;
import model.ImageModel;
import model.ImageState;
import model.PixelRGB;
import model.RGBModel;
import org.junit.Before;
import org.junit.Test;

/**
 * Represents the tests for the RGBModel class. Ensures that all methods and constructors are
 * working as they are supposed to as defined by their descriptions.
 */
public class RGBModelTest {

  ImageModel flower;
  ImageModel flowerCopy;
  ImageModel flowerBrighter50;
  ImageModel flowerBrighter100;
  ImageModel flowerBrighter150;
  ImageModel flowerDarker50;
  ImageModel flowerDarker150;
  ImageModel flowerBlue;
  ImageModel flowerGreen;
  ImageModel flowerRed;
  ImageModel flowerVertical;
  ImageModel flowerHV;
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
  ImageModel flowerHorizontal;

  /**
   * Initializes values for testing. Ensures that mutation will not disrupt any tests.
   */
  @Before
  public void setUp() {
    fourByFour = new RGBModel(readFile("res/4by4.ppm"));
    fourByFourB1 = new RGBModel(readFile("res/4by4Blur.ppm"));
    fourByFourB2 = new RGBModel(readFile("res/4by4Blur2.ppm"));
    fourByFourS1 = new RGBModel(readFile("res/4by4Sharpen.ppm"));
    fourByFourS2 = new RGBModel(readFile("res/4by4Sharpen2.ppm"));
    fourByFourGrey = new RGBModel(readFile("res/4by4Greyscale.ppm"));
    fourByFourSepia = new RGBModel(readFile("res/4by4Sepia.ppm"));
    flower = new RGBModel(readFile("res/flower.ppm"));
    flowerCopy = new RGBModel(readFile("res/flower.ppm"));
    flowerBrighter50 = new RGBModel(readFile("res/flower-50brighter.ppm"));
    flowerBrighter100 = new RGBModel(readFile("res/flower-100brighter.ppm"));
    flowerBrighter150 = new RGBModel(readFile("res/flower-150brighter.ppm"));
    flowerDarker50 = new RGBModel(readFile("res/flower-50darker.ppm"));
    flowerDarker150 = new RGBModel(readFile("res/flower-150darker.ppm"));
    flowerBlue = new RGBModel(readFile("res/flower-blue.ppm"));
    flowerGreen = new RGBModel(readFile("res/flower-green.ppm"));
    flowerRed = new RGBModel(readFile("res/flower-red.ppm"));
    flowerVertical = new RGBModel(readFile("res/flower-vertical.ppm"));
    oneBlack = new RGBModel(readFile("res/one_black_pixel.ppm"));
    oneBlackCopy = new RGBModel(readFile("res/one_black_pixel.ppm"));
    hedgehog = new RGBModel(readFile("res/hedg_grayscale.ppm"));
  }

  @Test
  public void construct() {
    this.setUp();
    assertEquals(hedgehog.getImage(), readFile("res/hedg_grayscale.ppm"));
    assertEquals(flower.getImage(), readFile("res/flower.ppm"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructFail() {
    new RGBModel(readFile("res/flower-grayscale.ppm"));
  }


  @Test
  public void makeRedTest() {
    this.setUp();
    flower.makeRed();
    assertEquals(flowerRed, flower);
  }

  @Test
  public void makeGreenTest() {
    this.setUp();
    flower.makeGreen();
    assertEquals(flowerGreen, flower);
  }

  @Test
  public void makeBlueTest() {
    this.setUp();
    flower.makeBlue();
    assertEquals(flowerBlue, flower);
  }

  @Test
  public void imageValueTest() {
    Image check = new Image(
        new PixelRGB[flower.getImage().getImageHeight()][flower.getImage().getImageWidth()]);
    for (int r = 0; r < flower.getImage().getImageHeight(); r++) {
      System.out.println(r);
      for (int c = 0; c < flower.getImage().getImageWidth(); c++) {
        int value = flower.getImage().getImage()[r][c].computeValue();
        check.getImage()[r][c] = new PixelRGB(value, value, value);
      }
    }
    flower.imageValue();
    assertEquals(check, flower.getImage());

    oneBlack.imageValue();
    assertEquals(oneBlack, oneBlackCopy);
  }

  @Test
  public void imageLumaTest() {
    ImageState check = new Image(
        new PixelRGB[flower.getImage().getImageHeight()][flower.getImage().getImageWidth()]);
    for (int r = 0; r < flower.getImage().getImageHeight(); r++) {
      for (int c = 0; c < flower.getImage().getImageWidth(); c++) {
        int luma = flower.getImage().getImage()[r][c].computeLuma();
        check.getImage()[r][c] = new PixelRGB(luma, luma, luma);
      }
    }
    flower.imageLuma();
    assertEquals(check, hedgehog.getImage());

    oneBlack.imageLuma();
    assertEquals(oneBlack, oneBlackCopy);
  }

  @Test
  public void imageIntensityTest() {
    ImageState check = new Image(
        new PixelRGB[flower.getImage().getImageHeight()][flower.getImage().getImageWidth()]);
    for (int r = 0; r < flower.getImage().getImageHeight(); r++) {
      for (int c = 0; c < flower.getImage().getImageWidth(); c++) {
        int intensity = flower.getImage().getImage()[r][c].computeIntensity();
        check.getImage()[r][c] = new PixelRGB(intensity, intensity, intensity);
      }
    }
    flower.imageIntensity();
    assertEquals(check, hedgehog.getImage());

    oneBlack.imageIntensity();
    assertEquals(oneBlack, oneBlackCopy);
  }

  @Test
  public void flipHorizontalTest() {
    this.setUp();
    flower.flipHorizontal();
    assertEquals(flower, flowerHorizontal);
    flower.flipHorizontal();
    assertEquals(flowerCopy, flower);
  }

  @Test
  public void flipVerticalTest() {
    this.setUp();
    flower.flipVertical();
    assertEquals(flower, flowerVertical);
    flower.flipVertical();
    assertEquals(flowerCopy, flower);
  }

  @Test
  public void flipBothTest() {
    this.setUp();
    flower.flipHorizontal();
    flower.flipVertical();
    assertEquals(flower, flowerHV);
    flower.flipVertical();
    assertEquals(flower, flowerHorizontal);
    flower.flipHorizontal();
    assertEquals(flower, flowerCopy);
  }

  @Test
  public void brightenTest() {
    this.setUp();
    flower.brighten(50);
    assertEquals(flower, flowerBrighter50);
    flower.brighten(50);
    assertEquals(flower, flowerBrighter100);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negBright() {
    hedgehog.brighten(-1);
  }

  @Test
  public void noBrighterTest() {
    this.setUp();
    flower.brighten(50);
    assertEquals(flower, flowerBrighter150);
  }

  @Test
  public void darkenTest() {
    flower.darken(50);
    assertEquals(flower, flowerDarker50);
    flower.darken(100);
    assertEquals(flower, flowerDarker150);
  }

  @Test
  public void noDarkerTest() {
    oneBlack.darken(1);
    assertEquals(oneBlack, oneBlackCopy);
  }

  @Test(expected = IllegalArgumentException.class)
  public void negDark() {
    hedgehog.darken(-1);
  }


  @Test
  public void getImageTest() {
    assertEquals(flower.getImage(), readFile("res/flower.ppm"));
    assertEquals(oneBlack.getImage(), readFile("res/one_black_pixel.ppm"));
    assertEquals(hedgehog.getImage(), readFile("res/hedg_grayscale.ppm"));
  }

  @Test
  public void imageHeightTest() {
    assertEquals(oneBlack.getImage().getImageHeight(), 1);
    assertEquals(hedgehog.getImage().getImageHeight(), 213);
  }

  @Test
  public void imageWidthTest() {
    assertEquals(oneBlack.getImage().getImageWidth(), 1);
    assertEquals(hedgehog.getImage().getImageWidth(), 320);
  }

  @Test
  public void redValuePixelTest() {
    assertEquals(oneBlack.redValuePixel(0, 0), 0);
    assertEquals(flower.redValuePixel(5, 5), 32);
  }

  @Test
  public void greenValuePixelTest() {
    assertEquals(oneBlack.greenValuePixel(0, 0), 0);
    assertEquals(flower.greenValuePixel(5, 5), 9);
  }

  @Test
  public void blueValuePixelTest() {
    this.setUp();
    assertEquals(oneBlack.blueValuePixel(0, 0), 0);
    assertEquals(flower.blueValuePixel(5, 5), 97);
  }

  @Test
  public void equalsTest() {
    this.setUp();
    assertEquals(flower, flowerCopy);
    assertEquals(oneBlack, oneBlackCopy);
    assertNotEquals(flower, flowerHV);
  }

  @Test
  public void hashMapDifferentTest() {
    this.setUp();
    assertNotSame(flower.hashCode(), flowerVertical.hashCode());
    assertNotSame(hedgehog.hashCode(), flower.hashCode());
  }


  @Test
  public void gauss() {
    this.setUp();
    fourByFour.gaussianBlur();
    assertEquals(fourByFourB1, fourByFour);

    fourByFour.gaussianBlur();
    assertEquals(fourByFourB2, fourByFour);

  }

  @Test
  public void sharp() {
    this.setUp();
    fourByFour.sharpen();
    assertEquals(fourByFourS1, fourByFour);

    fourByFour.sharpen();
    assertEquals(fourByFourS2, fourByFour);
  }

  @Test
  public void grey() {
    this.setUp();
    fourByFour.greyscale();
    assertEquals(fourByFourGrey, fourByFour);

    oneBlack.greyscale();
    assertEquals(oneBlack, oneBlackCopy);
  }

  @Test
  public void sepia() {
    this.setUp();
    fourByFour.sepia();
    assertEquals(fourByFourSepia, fourByFour);

    oneBlack.sepia();
    assertEquals(oneBlack, oneBlackCopy);
  }

}