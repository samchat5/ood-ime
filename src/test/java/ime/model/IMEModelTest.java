package ime.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import cs3500.ime.GreyscaleComponent;
import cs3500.ime.image.ImageUtil;
import cs3500.ime.model.IIMEModel;
import cs3500.ime.model.IMEModel;

import java.io.File;
import java.io.FileNotFoundException;
import org.junit.Test;

public class IMEModelTest {

  private final String relPathToRes = "../../../../res/";

  @Test
  public void testLoad() {
    try {
      // test with known file name
      IIMEModel model = new IMEModel();
      model.load(relPathToRes + "PPMImages/Koala.ppm", "koala");
      model.load(relPathToRes + "PPMImages/testOG.ppm", "mytest");
      assertTrue(model.isLoaded("koala"));
      assertTrue(model.isLoaded("mytest"));
      assertFalse(model.isLoaded("random"));

      // overload images "realiases" the image
      model.load(relPathToRes + "PPMImages/Koala.ppm", "newKoala");
      assertFalse(model.isLoaded("koala"));
      assertFalse(model.isLoaded("newKoala"));

    } catch (Exception e) {
      fail();
    }
  }

  @Test(expected = FileNotFoundException.class)
  public void testLoadUnknownFile() throws FileNotFoundException {
    IIMEModel model = new IMEModel();
    model.load("this/doesnt/exist.ppm", "random");
  }

  @Test
  public void testBrighten() {
    try {
      IIMEModel model = new IMEModel();
      model.load(relPathToRes + "PPMImages/Koala.ppm", "koala");
      model.brighten("koala", 50, "koalaBright");

      model.load(relPathToRes + "PPMImages/testOG.ppm", "mytest");
      model.brighten("mytest", 50, "mytestBright");

      model.save("koalaBright.ppm", "koalaBright");
      assertEquals(ImageUtil.readPPM("koalaBright.ppm"), ImageUtil.readPPM(relPathToRes +
          "PPMImages/koala-brighter-by-50.ppm"));

      model.save("mytestBright.ppm", "mytestBright");
      assertEquals(ImageUtil.readPPM("mytestBright.ppm"), ImageUtil.readPPM(relPathToRes +
          "PPMImages/testBrightenBy50.ppm"));

      if (!new File("koalaBright.ppm").delete() || !new File("mytestBright.ppm").delete()) {
        fail("File not deleted");
      }
    } catch (Exception e) {
      fail();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBrightenUnloaded() {
    IIMEModel model = new IMEModel();
    model.brighten("koala", 50, "newKoala");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBrightenBelowNeg255() {
    try {
      IIMEModel model = new IMEModel();
      model.load(relPathToRes + "PPMImage/Koala.ppm", "koala");
      model.brighten("koala", -300, "newKoala");
    } catch (FileNotFoundException e) {
      fail();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBrightenAbovePos255() {
    try {
      IIMEModel model = new IMEModel();
      model.load(relPathToRes + "PPMImage/Koala.ppm", "koala");
      model.brighten("koala", 300, "newKoala");
    } catch (FileNotFoundException e) {
      fail();
    }
  }

  @Test
  public void testHorizontalFlip() {
    try {
      IIMEModel model = new IMEModel();
      model.load(relPathToRes + "PPMImages/Koala.ppm", "koala");
      model.horizontalFlip("koala", "koalaFlip");

      model.load(relPathToRes + "PPMImages/testOG.ppm", "mytest");
      model.horizontalFlip("mytest", "mytestFlip");

      model.save("koalaFlip.ppm", "koalaFlip");
      assertEquals(ImageUtil.readPPM("koalaFlip.ppm"), ImageUtil.readPPM(relPathToRes +
          "PPMImages/koala-horizontal.ppm"));

      model.save("mytestFlip.ppm", "mytestFlip");
      assertEquals(ImageUtil.readPPM("mytestFlip.ppm"), ImageUtil.readPPM(relPathToRes +
          "PPMImages/testHorizontal.ppm"));

      if (!new File("koalaFlip.ppm").delete() || !new File("mytestFlip.ppm").delete()) {
        fail("File not deleted");
      }
    } catch (Exception e) {
      fail();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHorizontalFlipUnloaded() {
    new IMEModel().horizontalFlip("koala", "koalaFlip");
  }

  @Test
  public void testVerticalFlip() {
    try {
      IIMEModel model = new IMEModel();
      model.load(relPathToRes + "PPMImages/Koala.ppm", "koala");
      model.verticalFlip("koala", "koalaFlip");

      model.load(relPathToRes + "PPMImages/testOG.ppm", "mytest");
      model.verticalFlip("mytest", "mytestFlip");

      model.save("koalaFlip.ppm", "koalaFlip");
      assertEquals(ImageUtil.readPPM("koalaFlip.ppm"), ImageUtil.readPPM(relPathToRes +
          "PPMImages/koala-vertical.ppm"));

      model.save("mytestFlip.ppm", "mytestFlip");
      assertEquals(ImageUtil.readPPM("mytestFlip.ppm"), ImageUtil.readPPM(relPathToRes +
          "PPMImages/testVertical.ppm"));

      if (!new File("koalaFlip.ppm").delete() || !new File("mytestFlip.ppm").delete()) {
        fail("File not deleted");
      }
    } catch (Exception e) {
      fail();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testVerticalFlipUnloaded() {
    new IMEModel().verticalFlip("koala", "koalaFlip");
  }


  @Test
  public void testHorizontalAndVerticalFlip() {
    try {
      IIMEModel model = new IMEModel();
      model.load(relPathToRes + "PPMImages/Koala.ppm", "koala");
      model.horizontalFlip("koala", "koalaFlip");
      model.verticalFlip("koalaFlip", "koalaFlip");

      model.load(relPathToRes + "PPMImages/testOG.ppm", "mytest");
      model.horizontalFlip("mytest", "mytestFlip");
      model.verticalFlip("mytestFlip", "mytestFlip");

      model.save("koalaFlip.ppm", "koalaFlip");
      assertEquals(ImageUtil.readPPM("koalaFlip.ppm"), ImageUtil.readPPM(relPathToRes +
          "PPMImages/koala-vertical-horizontal.ppm"));
      assertEquals(ImageUtil.readPPM("koalaFlip.ppm"), ImageUtil.readPPM(relPathToRes +
          "PPMImages/koala-horizontal-vertical.ppm"));

      model.save("mytestFlip.ppm", "mytestFlip");
      assertEquals(ImageUtil.readPPM("mytestFlip.ppm"), ImageUtil.readPPM(relPathToRes +
          "PPMImages/testHorizontalVertical.ppm"));
      assertEquals(ImageUtil.readPPM("mytestFlip.ppm"), ImageUtil.readPPM(relPathToRes +
          "PPMImages/testVerticalHorizontal.ppm"));

      if (!new File("koalaFlip.ppm").delete() || !new File("mytestFlip.ppm").delete()) {
        fail("File not deleted");
      }
    } catch (Exception e) {
      fail();
    }
  }

  @Test(expected = FileNotFoundException.class)
  public void testSaveFileNotFound() throws FileNotFoundException {
    IIMEModel model = new IMEModel();
    model.load(relPathToRes + "PPMImages/Koala.ppm", "koala");
    model.save("unknown/path.ppm", "koala");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testSaveUnloadedImage() {
    try {
      IIMEModel model = new IMEModel();
      model.save("test.ppm", "koala");
    } catch (FileNotFoundException e) {
      fail();
    }
  }

  @Test
  public void testGreyScale() {
    try {
      IIMEModel model = new IMEModel();
      model.load(relPathToRes + "PPMImages/Koala.ppm", "koala");
      model.load(relPathToRes + "PPMImages/testOG.ppm", "mytest");

      model.greyScale("koala", "koalaGrey", GreyscaleComponent.RED);
      model.greyScale("mytest", "mytestGrey", GreyscaleComponent.RED);
      model.save("koalaGrey.ppm", "koalaGrey");
      model.save("mytestGrey.ppm", "mytestGrey");
      assertEquals(ImageUtil.readPPM("koalaGrey.ppm"), ImageUtil.readPPM(relPathToRes +
          "PPMImages/koala-red-greyscale.ppm"));
      assertEquals(ImageUtil.readPPM("mytestGrey.ppm"), ImageUtil.readPPM(relPathToRes +
          "PPMImages/testRed.ppm"));

      model.greyScale("koala", "koalaGrey", GreyscaleComponent.BLUE);
      model.greyScale("mytest", "mytestGrey", GreyscaleComponent.BLUE);
      model.save("koalaGrey.ppm", "koalaGrey");
      model.save("mytestGrey.ppm", "mytestGrey");
      assertEquals(ImageUtil.readPPM("koalaGrey.ppm"), ImageUtil.readPPM(relPathToRes +
          "PPMImages/koala-blue-greyscale.ppm"));
      assertEquals(ImageUtil.readPPM("mytestGrey.ppm"), ImageUtil.readPPM(relPathToRes +
          "PPMImages/testBlue.ppm"));

      model.greyScale("koala", "koalaGrey", GreyscaleComponent.GREEN);
      model.greyScale("mytest", "mytestGrey", GreyscaleComponent.GREEN);
      model.save("koalaGrey.ppm", "koalaGrey");
      model.save("mytestGrey.ppm", "mytestGrey");
      assertEquals(ImageUtil.readPPM("koalaGrey.ppm"), ImageUtil.readPPM(relPathToRes +
          "PPMImages/koala-green-greyscale.ppm"));
      assertEquals(ImageUtil.readPPM("mytestGrey.ppm"), ImageUtil.readPPM(relPathToRes +
          "PPMImages/testGreen.ppm"));

      model.greyScale("koala", "koalaGrey", GreyscaleComponent.LUMA);
      model.greyScale("mytest", "mytestGrey", GreyscaleComponent.LUMA);
      model.save("koalaGrey.ppm", "koalaGrey");
      model.save("mytestGrey.ppm", "mytestGrey");
      assertEquals(ImageUtil.readPPM("koalaGrey.ppm"), ImageUtil.readPPM(relPathToRes +
          "PPMImages/koala-luma-greyscale.ppm"));
      assertEquals(ImageUtil.readPPM("mytestGrey.ppm"), ImageUtil.readPPM(relPathToRes +
          "PPMImages/testLuma.ppm"));

      model.greyScale("koala", "koalaGrey", GreyscaleComponent.VALUE);
      model.greyScale("mytest", "mytestGrey", GreyscaleComponent.VALUE);
      model.save("koalaGrey.ppm", "koalaGrey");
      model.save("mytestGrey.ppm", "mytestGrey");
      assertEquals(ImageUtil.readPPM("koalaGrey.ppm"), ImageUtil.readPPM(relPathToRes +
          "PPMImages/koala-value-greyscale.ppm"));
      assertEquals(ImageUtil.readPPM("mytestGrey.ppm"), ImageUtil.readPPM(relPathToRes +
          "PPMImages/testValue.ppm"));

      model.greyScale("koala", "koalaGrey", GreyscaleComponent.INTENSITY);
      model.greyScale("mytest", "mytestGrey", GreyscaleComponent.INTENSITY);
      model.save("koalaGrey.ppm", "koalaGrey");
      model.save("mytestGrey.ppm", "mytestGrey");
      assertEquals(ImageUtil.readPPM("koalaGrey.ppm"), ImageUtil.readPPM(relPathToRes +
          "PPMImages/koala-intensity-greyscale.ppm"));
      assertEquals(ImageUtil.readPPM("mytestGrey.ppm"), ImageUtil.readPPM(relPathToRes +
          "PPMImages/testIntensity.ppm"));
    } catch (Exception e) {
      fail();
    }
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGreyscaleUnloadedImage() {
    new IMEModel().greyScale("koala", "koalaGrey", GreyscaleComponent.RED);
  }
}