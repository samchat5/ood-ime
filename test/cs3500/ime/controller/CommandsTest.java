package cs3500.ime.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import cs3500.ime.controller.commands.Brighten;
import cs3500.ime.controller.commands.GreyScale;
import cs3500.ime.controller.commands.HorizontalFlip;
import cs3500.ime.controller.commands.Load;
import cs3500.ime.controller.commands.Save;
import cs3500.ime.controller.commands.VerticalFlip;
import cs3500.ime.model.GreyscaleComponent;
import cs3500.ime.model.IIMEModel;
import cs3500.ime.model.IMEModel;
import cs3500.ime.model.image.IImage;
import cs3500.ime.model.image.ImageUtil;
import cs3500.ime.model.image.PPMImage;
import cs3500.ime.model.image.pixel.IPixel;
import java.io.File;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for all Command classes.
 */
public class CommandsTest {

  private final IImage emptyImage = new PPMImage(0, 0, new IPixel[][]{});
  private IIMEModel model;

  @Before
  public void setUp() {
    model = new IMEModel();
  }

  // Brighten

  @Test(expected = IllegalStateException.class)
  public void testNullNewImageNameBrighten() {
    model.load(emptyImage, "mytest");
    new Brighten(40, "mytest", null).run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testNullImageNameBrighten() {
    model.load(emptyImage, "mytest");
    new Brighten(40, null, "mytest2").run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidCommandBrighten() {
    model.load(emptyImage, "mytest");
    new Brighten(-300, "mytest", "myTestNew").run(model);
  }

  @Test
  public void testNormalUsageBrighten() {
    model.load(emptyImage, "mytest");
    new Brighten(20, "mytest", "mytest2").run(model);
    assertEquals(emptyImage, model.save("mytest2"));
  }

  // Greyscale

  @Test(expected = IllegalStateException.class)
  public void testNullNewImageNameGreyscale() {
    model.load(emptyImage, "mytest");
    new GreyScale("mytest", null, GreyscaleComponent.BLUE).run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testNullImageNameGreyscale() {
    model.load(emptyImage, "mytest");
    new GreyScale(null, "mytest", GreyscaleComponent.BLUE).run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testNullComponentGreyScale() {
    model.load(emptyImage, "mytest");
    new GreyScale("mytest", "mytest", null).run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidGreyscale() {
    IIMEModel model = new IMEModel();
    new GreyScale("mytest", "mytest", GreyscaleComponent.BLUE).run(model);
  }

  @Test
  public void testNormalUsageGreyscale() {
    model.load(emptyImage, "mytest");
    for (GreyscaleComponent c : GreyscaleComponent.values()) {
      new GreyScale("mytest", "mytestGrey", c).run(model);
      assertEquals(model.save("mytestGrey"), emptyImage);
    }
  }

  // Horizontal Flip

  @Test(expected = IllegalStateException.class)
  public void testNullImageNameHorizontal() {
    model.load(emptyImage, "mytest");
    new HorizontalFlip(null, "mytest").run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testNullNewImageNameHorizontal() {
    model.load(emptyImage, "mytest");
    new HorizontalFlip("mytest", null).run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidHorizontalFlip() {
    new HorizontalFlip("mytest", "mytest").run(model);
  }

  @Test
  public void testNormalUsageHorizontalFlip() {
    model.load(emptyImage, "mytest");
    new HorizontalFlip("mytest", "mytest").run(model);
    assertEquals(model.save("mytest"), emptyImage);
  }

  // Vertical Flip

  @Test(expected = IllegalStateException.class)
  public void testNullImageNameVertical() {
    model.load(emptyImage, "mytest");
    new VerticalFlip(null, "mytest").run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testNullNewImageNameVertical() {
    model.load(emptyImage, "mytest");
    new VerticalFlip("mytest", null).run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidVerticalFlip() {
    new VerticalFlip("mytest", "mytest").run(model);
  }

  @Test
  public void testNormalUsageVerticalFlip() {
    model.load(emptyImage, "mytest");
    new VerticalFlip("mytest", "mytest").run(model);
    assertEquals(model.save("mytest"), emptyImage);
  }

  // Load

  @Test(expected = IllegalStateException.class)
  public void testNullFilePathLoad() {
    new Load(null, "mytest").run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testNullImageNameLoad() {
    new Load("res/PPMImages/testOG.ppm", null).run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidLoad() {
    new Load("res/PPMImages/something.ppm", "mytest").run(model);
  }

  @Test
  public void testNormalUsageLoad() {
    new Load("res/PPMImages/testOG.ppm", "mytest").run(model);
    assertEquals(ImageUtil.readPPM("res/PPMImages/testOG.ppm"), model.save("mytest"));
  }

  // Save

  @Test(expected = IllegalStateException.class)
  public void testNullFilePathSave() {
    model.load(emptyImage, "mytest");
    new Save(null, "mytest").run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testNullImageNameSave() {
    model.load(emptyImage, "mytest");
    new Save("test/test.ppm", null).run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidSave() {
    model.load(emptyImage, "mytest");
    new Save("testing123/test.ppm", "mytest").run(model);
  }

  @Test
  public void testNormalUsageSav() {
    model.load(emptyImage, "mytest");
    new Save("test/test.ppm", "mytest").run(model);
    assertEquals(ImageUtil.readPPM("test/test.ppm"), emptyImage);
    if (!new File("test/test.ppm").delete()) {
      fail();
    }
  }
}
