package cs3500.ime.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import cs3500.ime.image.ImageUtil;
import cs3500.ime.model.IMEModel;
import cs3500.ime.view.IMETextView;
import java.io.File;
import java.io.StringReader;
import org.junit.Test;

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
    if (!new File("test/koalaTest.ppm").delete()) {
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
    String in = "save unknown/path.ppm koala\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Save file not found.");
  }

  @Test
  public void testUnknownCommand() {
    StringBuilder app = new StringBuilder();
    String in = "some-command res/PPMImages/Koala.ppm\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Unknown command.");
  }

  @Test
  public void testLoadFileNotFound() {
    StringBuilder app = new StringBuilder();
    String in = "load unknown/path.ppm koala\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Load file not found.");
  }

  @Test
  public void testInvalidRed() {
    StringBuilder app = new StringBuilder();
    String in = "red-component koala koalaGrey\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Invalid red component command.");
  }

  @Test
  public void testInvalidBlue() {
    StringBuilder app = new StringBuilder();
    String in = "blue-component koala koalaGrey\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Invalid blue component command.");
  }

  @Test
  public void testInvalidGreen() {
    StringBuilder app = new StringBuilder();
    String in = "green-component koala koalaGrey\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Invalid green component command.");
  }

  @Test
  public void testInvalidLuma() {
    StringBuilder app = new StringBuilder();
    String in = "luma-component koala koalaGrey\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Invalid luma component command.");
  }

  @Test
  public void testInvalidIntensity() {
    StringBuilder app = new StringBuilder();
    String in = "intensity-component koala koalaGrey\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Invalid intensity component command.");
  }

  @Test
  public void testInvalidValue() {
    StringBuilder app = new StringBuilder();
    String in = "value-component koala koalaGrey\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Invalid value component command.");
  }

  @Test
  public void testRedComponent() {
    StringBuilder app = new StringBuilder();
    String in = "load PPMImages/Koala.ppm koala\n"
        + "load PPMImages/testOG.ppm mytest\n"
        + "red-component koala koalaGrey\n"
        + "red-component mytest mytestGrey\n"
        + "save test/mytestGrey.ppm mytestGrey\n"
        + "save test/koalaGrey.ppm koalaGrey\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/koalaGrey.ppm"), ImageUtil.readPPM(
        "res/PPMImages/koala-red-greyscale.ppm"));
    assertEquals(ImageUtil.readPPM("test/mytestGrey.ppm"), ImageUtil.readPPM(
        "res/PPMImages/testRed.ppm"));
    if (!new File("test/koalaGrey.ppm").delete() || !new File("test/koalaGrey.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testBlueComponent() {
    StringBuilder app = new StringBuilder();
    String in = "load PPMImages/Koala.ppm koala\n"
        + "load PPMImages/testOG.ppm mytest\n"
        + "blue-component koala koalaGrey\n"
        + "blue-component mytest mytestGrey\n"
        + "save test/mytestGrey.ppm mytestGrey\n"
        + "save test/koalaGrey.ppm koalaGrey\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/koalaGrey.ppm"), ImageUtil.readPPM(
        "res/PPMImages/koala-blue-greyscale.ppm"));
    assertEquals(ImageUtil.readPPM("test/mytestGrey.ppm"), ImageUtil.readPPM(
        "res/PPMImages/testBlue.ppm"));
    if (!new File("test/koalaGrey.ppm").delete() || !new File("test/koalaGrey.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testGreenComponent() {
    StringBuilder app = new StringBuilder();
    String in = "load PPMImages/Koala.ppm koala\n"
        + "load PPMImages/testOG.ppm mytest\n"
        + "green-component koala koalaGrey\n"
        + "green-component mytest mytestGrey\n"
        + "save test/mytestGrey.ppm mytestGrey\n"
        + "save test/koalaGrey.ppm koalaGrey\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/koalaGrey.ppm"), ImageUtil.readPPM(
        "res/PPMImages/koala-green-greyscale.ppm"));
    assertEquals(ImageUtil.readPPM("test/mytestGrey.ppm"), ImageUtil.readPPM(
        "res/PPMImages/testGreen.ppm"));
    if (!new File("test/koalaGrey.ppm").delete() || !new File("test/koalaGrey.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testLumaComponent() {
    StringBuilder app = new StringBuilder();
    String in = "load PPMImages/Koala.ppm koala\n"
        + "load PPMImages/testOG.ppm mytest\n"
        + "luma-component koala koalaGrey\n"
        + "luma-component mytest mytestGrey\n"
        + "save test/mytestGrey.ppm mytestGrey\n"
        + "save test/koalaGrey.ppm koalaGrey\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/koalaGrey.ppm"), ImageUtil.readPPM(
        "res/PPMImages/koala-luma-greyscale.ppm"));
    assertEquals(ImageUtil.readPPM("test/mytestGrey.ppm"), ImageUtil.readPPM(
        "res/PPMImages/testLuma.ppm"));
    if (!new File("test/koalaGrey.ppm").delete() || !new File("test/koalaGrey.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testValueComponent() {
    StringBuilder app = new StringBuilder();
    String in = "load PPMImages/Koala.ppm koala\n"
        + "load PPMImages/testOG.ppm mytest\n"
        + "value-component koala koalaGrey\n"
        + "value-component mytest mytestGrey\n"
        + "save test/mytestGrey.ppm mytestGrey\n"
        + "save test/koalaGrey.ppm koalaGrey\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/koalaGrey.ppm"), ImageUtil.readPPM(
        "res/PPMImages/koala-value-greyscale.ppm"));
    assertEquals(ImageUtil.readPPM("test/mytestGrey.ppm"), ImageUtil.readPPM(
        "res/PPMImages/testValue.ppm"));
    if (!new File("test/koalaGrey.ppm").delete() || !new File("test/mytestGrey.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testIntensityComponent() {
    StringBuilder app = new StringBuilder();
    String in = "load PPMImages/Koala.ppm koala\n"
        + "load PPMImages/testOG.ppm mytest\n"
        + "intensity-component koala koalaGrey\n"
        + "intensity-component mytest mytestGrey\n"
        + "save test/mytestGrey.ppm mytestGrey\n"
        + "save test/koalaGrey.ppm koalaGrey\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/koalaGrey.ppm"), ImageUtil.readPPM(
        "res/PPMImages/koala-intensity-greyscale.ppm"));
    assertEquals(ImageUtil.readPPM("test/mytestGrey.ppm"), ImageUtil.readPPM(
        "res/PPMImages/testIntensity.ppm"));
    if (!new File("test/koalaGrey.ppm").delete() || !new File("test/mytestGrey.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testBrighten() {
    StringBuilder app = new StringBuilder();
    String in = "load PPMImages/Koala.ppm koala\n"
        + "load PPMImages/testOG.ppm mytest\n"
        + "brighten 50 koala koalaBright\n"
        + "brighten 50 mytest mytestBright\n"
        + "save test/mytestBright.ppm mytestBright\n"
        + "save test/koalaBright.ppm koalaBright\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();

    assertEquals(ImageUtil.readPPM("test/koalaBright.ppm"), ImageUtil.readPPM(
        "res/PPMImages/koala-brighter-by-50.ppm"));
    assertEquals(ImageUtil.readPPM("test/mytestBright.ppm"), ImageUtil.readPPM(
        "res/PPMImages/testBrightenBy50.ppm"));

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
    assertEquals(app.toString(), "Invalid brightening command.");
  }

  @Test
  public void testBrightenAboveRange() {
    StringBuilder app = new StringBuilder();
    String in = "load PPMImages/Koala.ppm koala\n" +
        " brighten 300 koala koalaBright\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Invalid brightening command.");
  }

  @Test
  public void testBrightenBelowRange() {
    StringBuilder app = new StringBuilder();
    String in = "load PPMImages/Koala.ppm koala\n" +
        " brighten -300 koala koalaBright\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Invalid brightening command.");
  }

  @Test
  public void testHorizontalFlip() {
    StringBuilder app = new StringBuilder();
    String in = "load res/PPMImages/Koala.ppm koala\n" +
        "load res/PPMImages/mytest.ppm mytest\n" +
        "horizontal-flip koala koalaFlip\n" +
        "horizontal-flip mytest mytestFlip\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/koalaFlip"), ImageUtil.readPPM(
        "res/PPMImages/koala-horizontal.ppm"));
    assertEquals(ImageUtil.readPPM("test/mytestFlip"), ImageUtil.readPPM(
        "res/PPMImages/testHorizontal.ppm"));
  }

  @Test
  public void testHorizontalFlipUnloaded() {
    StringBuilder app = new StringBuilder();
    String in = "horizontal-flip koala koalaFlip\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Invalid horizontal flip command.");
  }

  @Test
  public void testVerticalFlip() {
    StringBuilder app = new StringBuilder();
    String in = "load res/PPMImages/Koala.ppm koala\n" +
        "load res/PPMImages/mytest.ppm mytest\n" +
        "vertical-flip koala koalaFlip\n" +
        "vertical-flip mytest mytestFlip\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(ImageUtil.readPPM("test/koalaFlip"), ImageUtil.readPPM(
        "res/PPMImages/koala-vertical.ppm"));
    assertEquals(ImageUtil.readPPM("test/mytestFlip"), ImageUtil.readPPM(
        "res/PPMImages/testVertical.ppm"));
  }

  @Test
  public void testVerticalFlipUnloaded() {
    StringBuilder app = new StringBuilder();
    String in = "vertical-flip koala koalaFlip\nquit\n";
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(app),
        new StringReader(in));
    cont.run();
    assertEquals(app.toString(), "Invalid vertical flip command.");
  }
}