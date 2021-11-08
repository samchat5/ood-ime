package cs3500.ime.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.ime.model.image.IImage;
import cs3500.ime.model.image.ImageUtil;
import cs3500.ime.model.image.color_transform.BlueComponent;
import cs3500.ime.model.image.color_transform.GreenComponent;
import cs3500.ime.model.image.color_transform.Luma;
import cs3500.ime.model.image.color_transform.RedComponent;
import cs3500.ime.model.image.color_transform.Sepia;
import cs3500.ime.model.image.filter.Blur;
import cs3500.ime.model.image.filter.Sharpen;
import org.junit.Test;

/**
 * Tests for IMEModel.
 */
public class IMEModelTest {

  private final String relPathToRes = "res/";
  private final IImage koala;
  private final IImage testOG;
  private final IImage koalaPng;
  private final IImage testOGPng;

  /**
   * Constructor that initializes image objects to speed up tests.
   */
  public IMEModelTest() {
    this.koala = ImageUtil.readPPM(relPathToRes + "PPMImages/Koala.ppm");
    this.testOG = ImageUtil.readPPM(relPathToRes + "PPMImages/testOG.ppm");
    this.koalaPng = ImageUtil.readImageIO(relPathToRes + "PNGImages/koala.png");
    this.testOGPng = ImageUtil.readImageIO(relPathToRes + "PNGImages/testOG.png");
  }

  @Test
  public void testIsLoaded() {
    IIMEModel model = new IMEModel();
    model.load(this.koala, "koala");
    model.load(this.testOG, "mytest");
    assertTrue(model.isLoaded("koala"));
    assertTrue(model.isLoaded("mytest"));
    assertFalse(model.isLoaded("random"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIsLoadedNull() {
    IIMEModel model = new IMEModel();
    model.isLoaded(null);
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

  @Test(expected = IllegalArgumentException.class)
  public void testLoadNullName() {
    IIMEModel model = new IMEModel();
    model.load(this.koala, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testLoadNullImage() {
    IIMEModel model = new IMEModel();
    model.load(null, "koala");
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
  public void testBrightenNullImageName() {
    IIMEModel model = new IMEModel();
    model.load(this.koala, "koala");
    model.brighten(null, 50, "koalaBright");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBrightenNullNewImageName() {
    IIMEModel model = new IMEModel();
    model.load(this.koala, "koala");
    model.brighten("koala", 50, null);
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

  @Test(expected = IllegalArgumentException.class)
  public void testHorizontalFlipNullName() {
    IIMEModel model = new IMEModel();
    model.load(this.koala, "koala");
    model.horizontalFlip(null, "koalaFlip");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHorizontalFlipNullNewName() {
    IIMEModel model = new IMEModel();
    model.load(this.koala, "koala");
    model.horizontalFlip("koala", null);
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

  @Test(expected = IllegalArgumentException.class)
  public void testVerticalFlipNullName() {
    IIMEModel model = new IMEModel();
    model.load(this.koala, "koala");
    model.verticalFlip(null, "koalaFlip");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testVerticalFlipNullNewName() {
    IIMEModel model = new IMEModel();
    model.load(this.koala, "koala");
    model.verticalFlip("koala", null);
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

  @Test(expected = IllegalArgumentException.class)
  public void testSaveNullImage() {
    IIMEModel model = new IMEModel();
    model.load(this.koala, "koala");
    model.save(null);
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

  @Test(expected = IllegalArgumentException.class)
  public void testGreyScaleNullImageName() {
    IIMEModel model = new IMEModel();
    model.load(this.koala, "koala");
    model.load(this.testOG, "mytest");
    model.greyScale(null, "koalaGrey", GreyscaleComponent.GREEN);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreyScaleNullNewImageName() {
    IIMEModel model = new IMEModel();
    model.load(this.koala, "koala");
    model.load(this.testOG, "mytest");
    model.greyScale("koala", null, GreyscaleComponent.GREEN);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreyScaleNullComponent() {
    IIMEModel model = new IMEModel();
    model.load(this.koala, "koala");
    model.load(this.testOG, "mytest");
    model.greyScale("koala", "koalaGrey", null);
  }

  @Test
  public void testBlurFilter() {
    IIMEModel model = new IMEModel();
    model.load(koalaPng, "koala");
    model.load(testOGPng, "testOG");

    model.filter("koala", "koalaBlur", new Blur());
    model.filter("testOG", "testOGBlur", new Blur());
    assertEquals(model.save("koalaBlur"), ImageUtil.readImageIO(relPathToRes + "PNGImages/koala"
        + "-blurred.png"));
    assertEquals(model.save("testOGBlur"),
        ImageUtil.readImageIO(relPathToRes + "PNGImages/testBlurred.png"));
  }

  @Test
  public void testSharpenFilter() {
    IIMEModel model = new IMEModel();
    model.load(koalaPng, "koala");
    model.load(testOGPng, "testOG");

    model.filter("koala", "koalaSharpen", new Sharpen());
    model.filter("testOG", "testOGSharpen", new Sharpen());
    assertEquals(model.save("koalaSharpen"), ImageUtil.readImageIO(relPathToRes + "PNGImages/koala"
        + "-sharpened.png"));
    assertEquals(model.save("testOGSharpen"), ImageUtil.readImageIO(relPathToRes +
        "PNGImages/testSharpened.png"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFilterNullName() {
    IIMEModel model = new IMEModel();
    model.load(this.testOGPng, "test");
    model.filter(null, "testNew", new Blur());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFilterNullNewName() {
    IIMEModel model = new IMEModel();
    model.load(this.testOGPng, "test");
    model.filter("test", null, new Sharpen());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFilterNullFilter() {
    IIMEModel model = new IMEModel();
    model.load(this.testOGPng, "test");
    model.filter("test", "testNew", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFilterUnloaded() {
    IIMEModel model = new IMEModel();
    model.filter("test", "test", new Blur());
  }

  @Test
  public void testSepia() {
    IIMEModel model = new IMEModel();
    model.load(this.testOGPng, "test");
    model.load(this.koalaPng, "koala");

    model.colorTransform("test", "testSepia", new Sepia());
    model.colorTransform("koala", "koalaSepia", new Sepia());
    assertEquals(model.save("testSepia"), ImageUtil.readImageIO(relPathToRes + "PNGImages"
        + "/testSepia.png"));
    assertEquals(model.save("koalaSepia"), ImageUtil.readImageIO(relPathToRes + "PNGImages"
        + "/koala-sepia.png"));
  }

  @Test
  public void testGreyScaleUsingTransform() {
    IIMEModel model = new IMEModel();
    model.load(this.koala, "koala");
    model.load(this.testOG, "mytest");

    model.colorTransform("koala", "koalaGrey", new RedComponent());
    model.colorTransform("mytest", "mytestGrey", new RedComponent());
    assertEquals(model.save("koalaGrey"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/koala-red-greyscale.ppm"));
    assertEquals(model.save("mytestGrey"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/testRed.ppm"));

    model.colorTransform("koala", "koalaGrey", new BlueComponent());
    model.colorTransform("mytest", "mytestGrey", new BlueComponent());
    assertEquals(model.save("koalaGrey"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/koala-blue-greyscale.ppm"));
    assertEquals(model.save("mytestGrey"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/testBlue.ppm"));

    model.colorTransform("koala", "koalaGrey", new GreenComponent());
    model.colorTransform("mytest", "mytestGrey", new GreenComponent());
    assertEquals(model.save("koalaGrey"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/koala-green-greyscale.ppm"));
    assertEquals(model.save("mytestGrey"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/testGreen.ppm"));

    model.colorTransform("koala", "koalaGrey", new Luma());
    model.colorTransform("mytest", "mytestGrey", new Luma());
    assertEquals(model.save("koalaGrey"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/koala-luma-greyscale.ppm"));
    assertEquals(model.save("mytestGrey"), ImageUtil.readPPM(relPathToRes +
        "PPMImages/testLuma.ppm"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTransformNullName() {
    IIMEModel model = new IMEModel();
    model.load(this.testOGPng, "test");
    model.colorTransform(null, "testNew", new Sepia());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTransformNullNewName() {
    IIMEModel model = new IMEModel();
    model.load(this.testOGPng, "test");
    model.colorTransform("test", null, new RedComponent());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTransformNullTransform() {
    IIMEModel model = new IMEModel();
    model.load(this.testOGPng, "test");
    model.colorTransform("test", "testNew", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testTransformUnloaded() {
    IIMEModel model = new IMEModel();
    model.colorTransform("test", "test", new Luma());
  }

}