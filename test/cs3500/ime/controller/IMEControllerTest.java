package cs3500.ime.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import cs3500.ime.model.IMEModel;
import cs3500.ime.model.image.ImageUtil;
import cs3500.ime.view.IMETextView;
import java.io.File;
import java.io.StringReader;
import org.junit.Test;

/**
 * Tests for IMEController.
 */
public class IMEControllerTest {

  @Test(expected = IllegalArgumentException.class)
  public void testNullConstructor() {
    new IMEController(null, null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullModelConstructor() {
    StringBuilder app = new StringBuilder();
    new IMEController(null, new IMETextView(app), new StringReader(""));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullViewConstructor() {
    new IMEController(new IMEModel(), null, new StringReader(""));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullReadableConstructor() {
    new IMEController(new IMEModel(), new IMETextView(new StringBuilder()), null);
  }

  @Test
  public void testLoadSave() {
    StringBuilder app = new StringBuilder();
    String in = "load res/PPMImages/Koala.ppm koala\n"
        + "load res/PPMImages/testOG.PPM mytest\n"
        + "save test/koalaTest.ppm koala\n"
        + "save test/mytest.ppm mytest\n"
        + "quit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/koalaTest.ppm"),
        ImageUtil.readPPM("res/PPMImages/Koala.ppm"));
    assertEquals(ImageUtil.readPPM("test/mytest.ppm"),
        ImageUtil.readPPM("res/PPMImages/testOG.ppm"));
    if (!new File("test/koalaTest.ppm").delete() || !new File("test/mytest.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testLoadSavePNG() {
    StringBuilder app = new StringBuilder();
    String in = "load res/PNGImages/Koala.png koala\n"
        + "load res/PNGImages/testOG.Png mytest\n"
        + "save test/koalaTest.png koala\n"
        + "save test/mytest.png mytest\n"
        + "quit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(ImageUtil.readImageIO("test/koalaTest.png"),
        ImageUtil.readImageIO("res/PNGImages/Koala.png"));
    assertEquals(ImageUtil.readImageIO("test/mytest.png"),
        ImageUtil.readImageIO("res/PNGImages/testOG.png"));
    if (!new File("test/koalaTest.png").delete() || !new File("test/mytest.png").delete()) {
      fail();
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testOutOfInputs() {
    StringBuilder app = new StringBuilder();
    String in = "load res/PPMImages.ppm Koala";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
  }


  @Test(expected = IllegalStateException.class)
  public void testIOException() {
    String in = "invalid-command-to-throw-error Koala";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(new MockAppendable()),
        new StringReader(in));
    cont.run();
  }

  @Test
  public void testSaveFileNotFound() {
    StringBuilder app = new StringBuilder();
    String in = "load res/PPMImages/Koala.ppm koala\nsave unknown/path.ppm koala\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Save file/image not found.\n");
  }

  @Test
  public void testSaveFileWithoutLoading() {
    StringBuilder app = new StringBuilder();
    String in = "save res/PPMImages/koala2.ppm koala\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Save file/image not found.\n");
  }

  @Test
  public void testUnknownCommand() {
    StringBuilder app = new StringBuilder();
    String in = "some-command res/PPMImages/Koala.ppm\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Unknown command.\n");
  }


  @Test
  public void testUnknownCommandMultipleArgs() {
    StringBuilder app = new StringBuilder();
    @SuppressWarnings("SpellCheckingInspection") String in = "some-command ei a q whe a a qwke e  a ksk  dad  \nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Unknown command.\n");
  }

  @Test
  public void testLoadFileNotFound() {
    StringBuilder app = new StringBuilder();
    String in = "load unknown/path.ppm koala\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Load file not found.\n");
  }

  @Test
  public void testInvalidRed() {
    StringBuilder app = new StringBuilder();
    String in = "red-component koala koalaGrey\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Invalid RED component command.\n");
  }

  @Test
  public void testInvalidBlue() {
    StringBuilder app = new StringBuilder();
    String in = "blue-component koala koalaGrey\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Invalid BLUE component command.\n");
  }

  @Test
  public void testInvalidGreen() {
    StringBuilder app = new StringBuilder();
    String in = "green-component koala koalaGrey\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Invalid GREEN component command.\n");
  }

  @Test
  public void testInvalidLuma() {
    StringBuilder app = new StringBuilder();
    String in = "luma-component koala koalaGrey\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Invalid LUMA component command.\n");
  }

  @Test
  public void testInvalidIntensity() {
    StringBuilder app = new StringBuilder();
    String in = "intensity-component koala koalaGrey\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Invalid INTENSITY component command.\n");
  }

  @Test
  public void testInvalidValue() {
    StringBuilder app = new StringBuilder();
    String in = "value-component koala koalaGrey\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Invalid VALUE component command.\n");
  }

  @Test
  public void testRedComponent() {
    StringBuilder app = new StringBuilder();
    String in = "load res/PPMImages/Koala.ppm koala\n"
        + "load res/PPMImages/testOG.ppm mytest\n"
        + "red-component koala koalaGrey\n"
        + "red-component mytest mytestGrey\n"
        + "save test/mytestGrey.ppm mytestGrey\n"
        + "save test/koalaGrey.ppm koalaGrey\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/koalaGrey.ppm"), ImageUtil.readImageIO(
        "res/PNGImages/koala-red-greyscale.png"));
    assertEquals(ImageUtil.readPPM("test/mytestGrey.ppm"), ImageUtil.readImageIO(
        "res/PNGImages/testRed.png"));
    if (!new File("test/koalaGrey.ppm").delete() || !new File("test/mytestGrey.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testBlueComponent() {
    StringBuilder app = new StringBuilder();
    String in = "load res/PPMImages/Koala.ppm koala\n"
        + "load res/PPMImages/testOG.ppm mytest\n"
        + "blue-component koala koalaGrey\n"
        + "blue-component mytest mytestGrey\n"
        + "save test/mytestGrey.ppm mytestGrey\n"
        + "save test/koalaGrey.ppm koalaGrey\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/koalaGrey.ppm"), ImageUtil.readImageIO(
        "res/PNGImages/koala-blue-greyscale.png"));
    assertEquals(ImageUtil.readPPM("test/mytestGrey.ppm"), ImageUtil.readImageIO(
        "res/PNGImages/testBlue.png"));
    if (!new File("test/koalaGrey.ppm").delete() || !new File("test/mytestGrey.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testGreenComponent() {
    StringBuilder app = new StringBuilder();
    String in = "load res/PPMImages/Koala.ppm koala\n"
        + "load res/PPMImages/testOG.ppm mytest\n"
        + "green-component koala koalaGrey\n"
        + "green-component mytest mytestGrey\n"
        + "save test/mytestGrey.ppm mytestGrey\n"
        + "save test/koalaGrey.ppm koalaGrey\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/koalaGrey.ppm"), ImageUtil.readImageIO(
        "res/PNGImages/koala-green-greyscale.png"));
    assertEquals(ImageUtil.readPPM("test/mytestGrey.ppm"), ImageUtil.readImageIO(
        "res/PNGImages/testGreen.png"));
    if (!new File("test/koalaGrey.ppm").delete() || !new File("test/mytestGrey.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testLumaComponent() {
    StringBuilder app = new StringBuilder();
    String in = "load res/PPMImages/Koala.ppm koala\n"
        + "load res/PPMImages/testOG.ppm mytest\n"
        + "luma-component koala koalaGrey\n"
        + "luma-component mytest mytestGrey\n"
        + "save test/mytestGrey.ppm mytestGrey\n"
        + "save test/koalaGrey.ppm koalaGrey\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/koalaGrey.ppm"), ImageUtil.readImageIO(
        "res/PNGImages/koala-luma-greyscale.png"));
    assertEquals(ImageUtil.readPPM("test/mytestGrey.ppm"), ImageUtil.readImageIO(
        "res/PNGImages/testLuma.png"));
    if (!new File("test/koalaGrey.ppm").delete() || !new File("test/mytestGrey.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testValueComponent() {
    StringBuilder app = new StringBuilder();
    String in = "load res/PPMImages/Koala.ppm koala\n"
        + "load res/PPMImages/testOG.ppm mytest\n"
        + "value-component koala koalaGrey\n"
        + "value-component mytest mytestGrey\n"
        + "save test/mytestGrey.ppm mytestGrey\n"
        + "save test/koalaGrey.ppm koalaGrey\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/koalaGrey.ppm"), ImageUtil.readImageIO(
        "res/PNGImages/koala-value-greyscale.png"));
    assertEquals(ImageUtil.readPPM("test/mytestGrey.ppm"), ImageUtil.readImageIO(
        "res/PNGImages/testValue.png"));
    if (!new File("test/koalaGrey.ppm").delete() || !new File("test/mytestGrey.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testIntensityComponent() {
    StringBuilder app = new StringBuilder();
    String in = "load res/PPMImages/Koala.ppm koala\n"
        + "load res/PPMImages/testOG.ppm mytest\n"
        + "intensity-component koala koalaGrey\n"
        + "intensity-component mytest mytestGrey\n"
        + "save test/mytestGrey.ppm mytestGrey\n"
        + "save test/koalaGrey.ppm koalaGrey\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/koalaGrey.ppm"), ImageUtil.readImageIO(
        "res/PNGImages/koala-intensity-greyscale.png"));
    assertEquals(ImageUtil.readPPM("test/mytestGrey.ppm"), ImageUtil.readImageIO(
        "res/PNGImages/testIntensity.png"));
    if (!new File("test/koalaGrey.ppm").delete() || !new File("test/mytestGrey.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testBrighten() {
    StringBuilder app = new StringBuilder();
    String in = "load res/PPMImages/Koala.ppm koala\n"
        + "load res/PPMImages/testOG.ppm mytest\n"
        + "brighten 50 koala koalaBright\n"
        + "brighten 50 mytest mytestBright\n"
        + "save test/mytestBright.ppm mytestBright\n"
        + "save test/koalaBright.ppm koalaBright\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();

    assertEquals(ImageUtil.readPPM("test/koalaBright.ppm"), ImageUtil.readImageIO(
        "res/PNGImages/koala-brighter-by-50.png"));
    assertEquals(ImageUtil.readPPM("test/mytestBright.ppm"), ImageUtil.readImageIO(
        "res/PNGImages/testBrightenBy50.png"));

    if (!new File("test/koalaBright.ppm").delete() || !new File("test/mytestBright.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testBrightenUnloaded() {
    StringBuilder app = new StringBuilder();
    String in = " brighten 50 koala koalaBright\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Invalid brightening command.\n");
  }

  @Test
  public void testBrightenAboveRange() {
    StringBuilder app = new StringBuilder();
    String in = "load res/PPMImages/Koala.ppm koala\n" +
        " brighten 300 koala koalaBright\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Invalid brightening command.\n");
  }

  @Test
  public void testBrightenBelowRange() {
    StringBuilder app = new StringBuilder();
    String in = "load res/PPMImages/Koala.ppm koala\n" +
        " brighten -300 koala koalaBright\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Invalid brightening command.\n");
  }

  @Test
  public void testHorizontalFlip() {
    StringBuilder app = new StringBuilder();
    String in = "load res/PPMImages/Koala.ppm koala\n" +
        "load res/PPMImages/testOG.ppm mytest\n" +
        "horizontal-flip koala koalaFlip\n" +
        "horizontal-flip mytest mytestFlip\n" +
        "save test/koalaFlip.ppm koalaFlip\n" +
        "save test/mytestFlip.ppm mytestFlip\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/koalaFlip.ppm"), ImageUtil.readImageIO(
        "res/PNGImages/koala-horizontal.png"));
    assertEquals(ImageUtil.readPPM("test/mytestFlip.ppm"), ImageUtil.readImageIO(
        "res/PNGImages/testHorizontal.png"));
    if (!new File("test/koalaFlip.ppm").delete() || !new File("test/mytestFlip.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testHorizontalFlipUnloaded() {
    StringBuilder app = new StringBuilder();
    String in = "horizontal-flip koala koalaFlip\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Invalid horizontal flip command.\n");
  }

  @Test
  public void testVerticalFlip() {
    StringBuilder app = new StringBuilder();
    String in = "load res/PPMImages/Koala.ppm koala\n" +
        "load res/PPMImages/testOG.ppm mytest\n" +
        "vertical-flip koala koalaFlip\n" +
        "vertical-flip mytest mytestFlip\n" +
        "save test/koalaFlip.ppm koalaFlip\n" +
        "save test/mytestFlip.ppm mytestFlip\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/koalaFlip.ppm"), ImageUtil.readImageIO(
        "res/PNGImages/koala-vertical.png"));
    assertEquals(ImageUtil.readPPM("test/mytestFlip.ppm"), ImageUtil.readImageIO(
        "res/PNGImages/testVertical.png"));
    if (!new File("test/koalaFlip.ppm").delete() || !new File("test/mytestFlip.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testInvalidCommandUsage() {
    StringBuilder app = new StringBuilder();
    String in = "load res/PPMImages/Koala.ppm koala\n" + "brighten koala 255 koalaBright\nquit";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Invalid command usage.\n");
  }

  @Test
  public void testVerticalFlipUnloaded() {
    StringBuilder app = new StringBuilder();
    String in = "vertical-flip koala koalaFlip\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Invalid vertical flip command.\n");
  }

  @Test
  public void testBlur() {
    StringBuilder app = new StringBuilder();
    String in = "load res/PNGImages/Koala.png koala\n"
        + "load res/PNGImages/testOG.png mytest\n"
        + "blur koala koalaBlur\n"
        + "blur mytest mytestBlur\n"
        + "save test/mytestBlur.png mytestBlur\n"
        + "save test/koalaBlur.png koalaBlur\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();

    assertEquals(ImageUtil.readImageIO("test/koalaBlur.png"), ImageUtil.readImageIO(
        "res/PNGImages/koala-blurred.png"));
    assertEquals(ImageUtil.readImageIO("test/mytestBlur.png"), ImageUtil.readImageIO(
        "res/PNGImages/testBlurred.png"));

    if (!new File("test/koalaBlur.png").delete() || !new File("test/mytestBlur.png").delete()) {
      fail();
    }
  }

  @Test
  public void testBlurUnloaded() {
    StringBuilder app = new StringBuilder();
    String in = " blur koala koalaBlur\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Illegal blur command.\n");
  }

  @Test
  public void testLumaTransform() {
    StringBuilder app = new StringBuilder();
    String in = "load res/PNGImages/Koala.png koala\n"
        + "load res/PNGImages/testOG.png mytest\n"
        + "greyscale koala koalaLuma\n"
        + "greyscale mytest mytestLuma\n"
        + "save test/mytestLuma.png mytestLuma\n"
        + "save test/koalaLuma.png koalaLuma\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();

    assertEquals(ImageUtil.readImageIO("test/koalaLuma.png"), ImageUtil.readImageIO(
        "res/PNGImages/koala-luma-greyscale.png"));
    assertEquals(ImageUtil.readImageIO("test/mytestLuma.png"), ImageUtil.readImageIO(
        "res/PNGImages/testLuma.png"));

    if (!new File("test/koalaLuma.png").delete() || !new File("test/mytestLuma.png").delete()) {
      fail();
    }
  }

  @Test
  public void testLumaUnloaded() {
    StringBuilder app = new StringBuilder();
    String in = " greyscale koala koalaLuma\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Invalid greyscale command.\n");
  }


  @Test
  public void testSepiaTransform() {
    StringBuilder app = new StringBuilder();
    String in = "load res/PNGImages/Koala.png koala\n"
        + "load res/PNGImages/testOG.png mytest\n"
        + "sepia koala koalaSepia\n"
        + "sepia mytest mytestSepia\n"
        + "save test/mytestSepia.png mytestSepia\n"
        + "save test/koalaSepia.png koalaSepia\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();

    assertEquals(ImageUtil.readImageIO("test/koalaSepia.png"), ImageUtil.readImageIO(
        "res/PNGImages/koala-sepia.png"));
    assertEquals(ImageUtil.readImageIO("test/mytestSepia.png"), ImageUtil.readImageIO(
        "res/PNGImages/testSepia.png"));

    if (!new File("test/koalaSepia.png").delete() || !new File("test/mytestSepia.png").delete()) {
      fail();
    }
  }

  @Test
  public void testSepiaUnloaded() {
    StringBuilder app = new StringBuilder();
    String in = "sepia koala koalaSepia\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Invalid Sepia command.\n");
  }

  @Test
  public void testSharpen() {
    StringBuilder app = new StringBuilder();
    String in = "load res/PNGImages/Koala.png koala\n"
        + "load res/PNGImages/testOG.png mytest\n"
        + "sharpen koala koalaSharpen\n"
        + "sharpen mytest mytestSharpen\n"
        + "save test/mytestSharpen.png mytestSharpen\n"
        + "save test/koalaSharpen.png koalaSharpen\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();

    assertEquals(ImageUtil.readImageIO("test/koalaSharpen.png"), ImageUtil.readImageIO(
        "res/PNGImages/koala-sharpened.png"));
    assertEquals(ImageUtil.readImageIO("test/mytestSharpen.png"), ImageUtil.readImageIO(
        "res/PNGImages/testSharpened.png"));

    if (!new File("test/koalaSharpen.png").delete() || !new File(
        "test/mytestSharpen.png").delete()) {
      fail();
    }
  }

  @Test
  public void testSharpenUnloaded() {
    StringBuilder app = new StringBuilder();
    String in = "sharpen koala koalaSepia\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Illegal Sharpen command.\n");
  }
}