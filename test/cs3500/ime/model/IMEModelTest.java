package cs3500.ime.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.ime.GreyscaleComponent;
import cs3500.ime.image.IImage;
import cs3500.ime.image.ImageUtil;
import org.junit.Test;

public class IMEModelTest {

  private final String relPathToRes = "res/";
  private final IImage koala;
  private final IImage testOG;

  /**
   * Constructor that initializes image objects to speed up tests
   */
  public IMEModelTest() {
    this.koala = ImageUtil.readPPM(relPathToRes + "PPMImages/Koala.ppm");
    this.testOG = ImageUtil.readPPM(relPathToRes + "PPMImages/testOG.ppm");
  }

  @Test
  public void testLoad() {
    // test with known file name
    IIMEModel model = new IMEModel();
    model.load(this.koala, "koala");
    model.load(this.testOG, "mytest");
    assertTrue(model.isLoaded("koala"));
    assertTrue(model.isLoaded("mytest"));
    assertFalse(model.isLoaded("random"));

    // overload images
    model.load(this.testOG, "koala");
    assertEquals(model.save("koala"), model.save("mytest"));
  }

  @Test
  public void testBrighten() {
    IIMEModel model = new IMEModel();
    model.load(this.koala, "koala");
    model.brighten("koala", 50, "koalaBright");

    model.load(this.testOG, "mytest");
    model.brighten("mytest", 50, "mytestBright");

    assertEquals(model.save("koalaBright"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/koala-brighter-by-50.ppm"));

    assertEquals(model.save("mytestBright"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/testBrightenBy50.ppm"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBrightenUnloaded() {
    IIMEModel model = new IMEModel();
    model.brighten("koala", 50, "newKoala");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBrightenBelowNeg255() {
    IIMEModel model = new IMEModel();
    model.load(this.koala, "koala");
    model.brighten("koala", -300, "newKoala");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBrightenAbovePos255() {
    IIMEModel model = new IMEModel();
    model.load(this.koala, "koala");
    model.brighten("koala", 300, "newKoala");
  }

  @Test
  public void testHorizontalFlip() {
    IIMEModel model = new IMEModel();
    model.load(this.koala, "koala");
    model.horizontalFlip("koala", "koalaFlip");

    model.load(this.testOG, "mytest");
    model.horizontalFlip("mytest", "mytestFlip");

    assertEquals(model.save("koalaFlip"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/koala-horizontal.ppm"));

    assertEquals(model.save("mytestFlip"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/testHorizontal.ppm"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHorizontalFlipUnloaded() {
    new IMEModel().horizontalFlip("koala", "koalaFlip");
  }

  @Test
  public void testVerticalFlip() {
    IIMEModel model = new IMEModel();
    model.load(this.koala, "koala");
    model.verticalFlip("koala", "koalaFlip");

    model.load(this.testOG, "mytest");
    model.verticalFlip("mytest", "mytestFlip");

    assertEquals(model.save("koalaFlip"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/koala-vertical.ppm"));

    assertEquals(model.save("mytestFlip"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/testVertical.ppm"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testVerticalFlipUnloaded() {
    new IMEModel().verticalFlip("koala", "koalaFlip");
  }

  @Test
  public void testHorizontalAndVerticalFlip() {
    IIMEModel model = new IMEModel();
    model.load(this.koala, "koala");
    model.horizontalFlip("koala", "koalaFlip");
    model.verticalFlip("koalaFlip", "koalaFlip");

    model.load(this.testOG, "mytest");
    model.horizontalFlip("mytest", "mytestFlip");
    model.verticalFlip("mytestFlip", "mytestFlip");

    assertEquals(model.save("koalaFlip"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/koala-vertical-horizontal.ppm"));
    assertEquals(model.save("koalaFlip"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/koala-horizontal-vertical.ppm"));

    assertEquals(model.save("mytestFlip"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/testHorizontalVertical.ppm"));
    assertEquals(model.save("mytestFlip"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/testVerticalHorizontal.ppm"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSaveUnloadedImage() {
    IIMEModel model = new IMEModel();
    model.save("koala");
  }

  @Test
  public void testGreyScale() {
    IIMEModel model = new IMEModel();
    model.load(this.koala, "koala");
    model.load(this.testOG, "mytest");

    model.greyScale("koala", "koalaGrey", GreyscaleComponent.RED);
    model.greyScale("mytest", "mytestGrey", GreyscaleComponent.RED);
    assertEquals(model.save("koalaGrey"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/koala-red-greyscale.ppm"));
    assertEquals(model.save("mytestGrey"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/testRed.ppm"));

    model.greyScale("koala", "koalaGrey", GreyscaleComponent.BLUE);
    model.greyScale("mytest", "mytestGrey", GreyscaleComponent.BLUE);
    assertEquals(model.save("koalaGrey"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/koala-blue-greyscale.ppm"));
    assertEquals(model.save("mytestGrey"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/testBlue.ppm"));

    model.greyScale("koala", "koalaGrey", GreyscaleComponent.GREEN);
    model.greyScale("mytest", "mytestGrey", GreyscaleComponent.GREEN);

    assertEquals(model.save("koalaGrey"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/koala-green-greyscale.ppm"));
    assertEquals(model.save("mytestGrey"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/testGreen.ppm"));

    model.greyScale("koala", "koalaGrey", GreyscaleComponent.LUMA);
    model.greyScale("mytest", "mytestGrey", GreyscaleComponent.LUMA);
    assertEquals(model.save("koalaGrey"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/koala-luma-greyscale.ppm"));
    assertEquals(model.save("mytestGrey"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/testLuma.ppm"));

    model.greyScale("koala", "koalaGrey", GreyscaleComponent.VALUE);
    model.greyScale("mytest", "mytestGrey", GreyscaleComponent.VALUE);
    assertEquals(model.save("koalaGrey"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/koala-value-greyscale.ppm"));
    assertEquals(model.save("mytestGrey"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/testValue.ppm"));

    model.greyScale("koala", "koalaGrey", GreyscaleComponent.INTENSITY);
    model.greyScale("mytest", "mytestGrey", GreyscaleComponent.INTENSITY);
    assertEquals(model.save("koalaGrey"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/koala-intensity-greyscale.ppm"));
    assertEquals(model.save("mytestGrey"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/testIntensity.ppm"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreyscaleUnloadedImage() {
    new IMEModel().greyScale("koala", "koalaGrey", GreyscaleComponent.RED);
  }
}