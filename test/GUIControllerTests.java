import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import cs3500.ime.controller.GuiController;
import cs3500.ime.controller.IGuiController;
import cs3500.ime.model.IMEModel;
import cs3500.ime.model.image.ImageUtil;
import cs3500.ime.view.GuiView;
import java.io.File;
import java.util.Scanner;
import org.junit.Test;

/**
 * Tests for GUI controller.
 */
public class GUIControllerTests {

  @Test(expected = IllegalArgumentException.class)
  public void testNullConstructor() {
    new GuiController(null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelConstructor() {
    new GuiController(null, new GuiView());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullViewConstructor() {
    new GuiController(new IMEModel(), null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testVoidSetScanner() {
    IGuiController cont = new GuiController(new IMEModel(), new GuiView());
    cont.setScanner(null);
  }

  @Test
  public void testLoadSave() {
    String in = "load res/PPMImages/testOG.PPM\n"
        + "save test/mytest.ppm\n";
    IGuiController cont = new GuiController(new IMEModel(), new GuiView());
    cont.setScanner(new Scanner(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/mytest.ppm"),
        ImageUtil.readPPM("res/PPMImages/testOG.ppm"));
    if (!new File("test/mytest.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testLoadSavePNG() {
    String in = "load res/PNGImages/testOG.Png\n"
        + "save test/mytest.png\n";
    IGuiController cont = new GuiController(new IMEModel(), new GuiView());
    cont.setScanner(new Scanner(in));
    cont.run();
    assertEquals(ImageUtil.readImage("test/mytest.png"),
        ImageUtil.readImage("res/PNGImages/testOG.png"));
    if (!new File("test/mytest.png").delete()) {
      fail();
    }
  }

  @Test
  public void testRedComponent() {
    String in = "load res/PPMImages/testOG.ppm\n"
        + "red-component\n"
        + "save test/mytestGrey.ppm\n";
    IGuiController cont = new GuiController(new IMEModel(), new GuiView());
    cont.setScanner(new Scanner(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/mytestGrey.ppm"), ImageUtil.readImage(
        "res/PNGImages/testRed.png"));
    if (!new File("test/mytestGrey.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testBlueComponent() {
    String in = "load res/PPMImages/testOG.ppm\n"
        + "blue-component\n"
        + "save test/mytestGrey.ppm\n";
    IGuiController cont = new GuiController(new IMEModel(), new GuiView());
    cont.setScanner(new Scanner(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/mytestGrey.ppm"), ImageUtil.readImage(
        "res/PNGImages/testBlue.png"));
    if (!new File("test/mytestGrey.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testGreenComponent() {
    String in = "load res/PPMImages/testOG.ppm\n"
        + "green-component\n"
        + "save test/mytestGrey.ppm\n";
    IGuiController cont = new GuiController(new IMEModel(), new GuiView());
    cont.setScanner(new Scanner(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/mytestGrey.ppm"), ImageUtil.readImage(
        "res/PNGImages/testGreen.png"));
    if (!new File("test/mytestGrey.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testLumaComponent() {
    String in = "load res/PPMImages/testOG.ppm\n"
        + "luma-component\n"
        + "save test/mytestGrey.ppm\n";
    IGuiController cont = new GuiController(new IMEModel(), new GuiView());
    cont.setScanner(new Scanner(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/mytestGrey.ppm"), ImageUtil.readImage(
        "res/PNGImages/testLuma.png"));
    if (!new File("test/mytestGrey.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testValueComponent() {
    String in = "load res/PPMImages/testOG.ppm\n"
        + "value-component\n"
        + "save test/mytestGrey.ppm\n";
    IGuiController cont = new GuiController(new IMEModel(), new GuiView());
    cont.setScanner(new Scanner(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/mytestGrey.ppm"), ImageUtil.readImage(
        "res/PNGImages/testValue.png"));
    if (!new File("test/mytestGrey.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testIntensityComponent() {
    String in = "load res/PPMImages/testOG.ppm\n"
        + "intensity-component\n"
        + "save test/mytestGrey.ppm\n";
    IGuiController cont = new GuiController(new IMEModel(), new GuiView());
    cont.setScanner(new Scanner(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/mytestGrey.ppm"), ImageUtil.readImage(
        "res/PNGImages/testIntensity.png"));
    if (!new File("test/mytestGrey.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testBrighten() {
    String in = "load res/PPMImages/testOG.ppm\n"
        + "brighten 50\n"
        + "save test/mytestBright.ppm\n";
    IGuiController cont = new GuiController(new IMEModel(), new GuiView());
    cont.setScanner(new Scanner(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/mytestBright.ppm"), ImageUtil.readImage(
        "res/PNGImages/testBrightenBy50.png"));
    if (!new File("test/mytestBright.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testHorizontalFlip() {
    String in = "load res/PPMImages/testOG.ppm\n" +
        "horizontal-flip\n" +
        "save test/mytestFlip.ppm\n";
    IGuiController cont = new GuiController(new IMEModel(), new GuiView());
    cont.setScanner(new Scanner(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/mytestFlip.ppm"), ImageUtil.readImage(
        "res/PNGImages/testHorizontal.png"));
    if (!new File("test/mytestFlip.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testVerticalFlip() {
    String in = "load res/PPMImages/testOG.ppm\n" +
        "vertical-flip\n" +
        "save test/mytestFlip.ppm\n";
    IGuiController cont = new GuiController(new IMEModel(), new GuiView());
    cont.setScanner(new Scanner(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/mytestFlip.ppm"), ImageUtil.readImage(
        "res/PNGImages/testVertical.png"));
    if (!new File("test/mytestFlip.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testBlur() {
    String in = "load res/PNGImages/testOG.png\n"
        + "blur\n"
        + "save test/mytestBlur.png\n";
    IGuiController cont = new GuiController(new IMEModel(), new GuiView());
    cont.setScanner(new Scanner(in));
    cont.run();
    assertEquals(ImageUtil.readImage("test/mytestBlur.png"), ImageUtil.readImage(
        "res/PNGImages/testBlurred.png"));
    if (!new File("test/mytestBlur.png").delete()) {
      fail();
    }
  }

  @Test
  public void testSepiaTransform() {
    String in = "load res/PNGImages/testOG.png\n"
        + "sepia\n"
        + "save test/mytestSepia.png\n";
    IGuiController cont = new GuiController(new IMEModel(), new GuiView());
    cont.setScanner(new Scanner(in));
    cont.run();
    assertEquals(ImageUtil.readImage("test/mytestSepia.png"), ImageUtil.readImage(
        "res/PNGImages/testSepia.png"));
    if (!new File("test/mytestSepia.png").delete()) {
      fail();
    }
  }

  @Test
  public void testSharpen() {
    String in = "load res/PNGImages/testOG.png\n"
        + "sharpen\n"
        + "save test/mytestSharpen.png\n";
    IGuiController cont = new GuiController(new IMEModel(), new GuiView());
    cont.setScanner(new Scanner(in));
    cont.run();
    assertEquals(ImageUtil.readImage("test/mytestSharpen.png"), ImageUtil.readImage(
        "res/PNGImages/testSharpened.png"));
    if (!new File("test/mytestSharpen.png").delete()) {
      fail();
    }
  }

  @Test
  public void testDownscaleLargeHeight() {
    String in = "load res/PNGImages/testOG.png\n"
        + "downscale 4 100000\n"
        + "save test/mytestDownscale.png\n";
    IGuiController cont = new GuiController(new IMEModel(), new GuiView());
    cont.setScanner(new Scanner(in));
    cont.run();
    assertEquals(ImageUtil.readImage("test/mytestDownscale.png"), ImageUtil.readImage(
        "res/PNGImages/testOG.png"));
    if (!new File("test/mytestDownscale.png").delete()) {
      fail();
    }
  }

  @Test
  public void testDownscaleLargeWidth() {
    String in = "load res/PNGImages/testOG.png\n"
        + "downscale 100000 4\n"
        + "save test/mytestDownscale.png\n";
    IGuiController cont = new GuiController(new IMEModel(), new GuiView());
    cont.setScanner(new Scanner(in));
    cont.run();
    assertEquals(ImageUtil.readImage("test/mytestDownscale.png"), ImageUtil.readImage(
        "res/PNGImages/testOG.png"));
    if (!new File("test/mytestDownscale.png").delete()) {
      fail();
    }
  }

  @Test
  public void testDownscaleLargeWidthHeight() {
    String in = "load res/PNGImages/testOG.png\n"
        + "downscale 100000 100000\n"
        + "save test/mytestDownscale.png\n";
    IGuiController cont = new GuiController(new IMEModel(), new GuiView());
    cont.setScanner(new Scanner(in));
    cont.run();
    assertEquals(ImageUtil.readImage("test/mytestDownscale.png"), ImageUtil.readImage(
        "res/PNGImages/testOG.png"));
    if (!new File("test/mytestDownscale.png").delete()) {
      fail();
    }
  }

  @Test
  public void testDownscaleNormalUsage() {
    String in = "load res/PNGImages/testOG.png\n"
        + "downscale 205 115\n"
        + "save test/mytestDownscale.png\n";
    IGuiController cont = new GuiController(new IMEModel(), new GuiView());
    cont.setScanner(new Scanner(in));
    cont.run();
    assertEquals(ImageUtil.readImage("test/mytestDownscale.png"), ImageUtil.readImage(
        "res/PNGImages/testDownscaleSameRatio.png"));
    if (!new File("test/mytestDownscale.png").delete()) {
      fail();
    }
  }
}