import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import cs3500.ime.controller.commands.BlurCommand;
import cs3500.ime.controller.commands.Brighten;
import cs3500.ime.controller.commands.Downscale;
import cs3500.ime.controller.commands.GreyScale;
import cs3500.ime.controller.commands.HorizontalFlip;
import cs3500.ime.controller.commands.Load;
import cs3500.ime.controller.commands.LumaTransform;
import cs3500.ime.controller.commands.Save;
import cs3500.ime.controller.commands.SepiaCommand;
import cs3500.ime.controller.commands.SharpenCommand;
import cs3500.ime.controller.commands.VerticalFlip;
import cs3500.ime.model.GreyscaleComponent;
import cs3500.ime.model.IIMEModel;
import cs3500.ime.model.IMEModel;
import cs3500.ime.model.image.IImage;
import cs3500.ime.model.image.Image;
import cs3500.ime.model.image.ImageUtil;
import cs3500.ime.model.image.pixel.IPixel;
import cs3500.ime.model.image.pixel.Pixel;
import java.io.File;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for all Command classes.
 */
public class CommandsTest {

  private final IImage emptyImage = new Image(0, 0, new IPixel[][]{});
  private IIMEModel model;

  @Before
  public void setUp() {
    model = new IMEModel();
  }

  // Blur

  @Test(expected = IllegalStateException.class)
  public void testNullImageNameBlur() {
    model.load(emptyImage, "mytest");
    new BlurCommand("mytest", null).run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testNullNewImageNameBlur() {
    model.load(emptyImage, "mytest");
    new BlurCommand(null, "mytest2").run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidCommandBlur() {
    new BlurCommand("mytest", "myTestNew").run(model);
  }

  @Test
  public void testNormalUsageBlur() {
    model.load(emptyImage, "mytest");
    new BlurCommand("mytest", "mytest2").run(model);
    assertEquals(emptyImage, model.save("mytest2"));
  }

  @Test(expected = IllegalStateException.class)
  public void testNullImageNameBlurMask() {
    model.load(emptyImage, "mytest");
    model.load(emptyImage, "mytest2");
    new BlurCommand("mytest", null, "mytest2").run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testNullNewImageNameBlurMask() {
    model.load(emptyImage, "mytest");
    model.load(emptyImage, "mytest2");
    new BlurCommand(null, "mytest", "mytest2").run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidCommandBlurMask() {
    new BlurCommand("mytest", "myTestNew", "mask").run(model);
  }

  @Test
  public void testNormalUsageBlurMask() {
    model.load(emptyImage, "mytest");
    model.load(emptyImage, "mask");
    new BlurCommand("mytest", "mytest2", "mask").run(model);
    assertEquals(emptyImage, model.save("mytest2"));
  }

  @Test(expected = IllegalStateException.class)
  public void testNullMaskImageName() {
    model.load(emptyImage, "mytest");
    new BlurCommand("mytest", "mytest", null).run(model);
  }

  // Luma Transform

  @Test(expected = IllegalStateException.class)
  public void testNullImageNameLumaTransform() {
    model.load(emptyImage, "mytest");
    new LumaTransform("mytest", null).run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testNullNewImageNameLumaTransform() {
    model.load(emptyImage, "mytest");
    new LumaTransform(null, "mytest").run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidCommandLumaTransform() {
    new LumaTransform("mytest", "myTestNew").run(model);
  }

  @Test
  public void testNormalUsageLumaTransform() {
    model.load(emptyImage, "mytest");
    new LumaTransform("mytest", "mytest2").run(model);
    assertEquals(emptyImage, model.save("mytest2"));
  }

  @Test(expected = IllegalStateException.class)
  public void testNullImageNameLumaTransformMask() {
    model.load(emptyImage, "mytest");
    model.load(emptyImage, "mask");
    new LumaTransform("mytest", null, "mask").run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testNullNewImageNameLumaTransformMask() {
    model.load(emptyImage, "mytest");
    model.load(emptyImage, "mask");
    new LumaTransform(null, "mytest", "mask").run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidCommandLumaTransformMask() {
    new LumaTransform("mytest", "myTestNew", "mask").run(model);
  }

  @Test
  public void testNormalUsageLumaTransformMask() {
    model.load(emptyImage, "mytest");
    model.load(emptyImage, "mask");
    new LumaTransform("mytest", "mytest2", "mask").run(model);
    assertEquals(emptyImage, model.save("mytest2"));
  }


  @Test(expected = IllegalStateException.class)
  public void testNullMaskImageNameLumaTransform() {
    model.load(emptyImage, "mytest");
    new LumaTransform("mytest", "mytest2", null).run(model);
    assertEquals(emptyImage, model.save("mytest2"));
  }

  // Sepia

  @Test(expected = IllegalStateException.class)
  public void testNullImageNameSepia() {
    model.load(emptyImage, "mytest");
    new SepiaCommand("mytest", null).run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testNullNewImageNameSepia() {
    model.load(emptyImage, "mytest");
    new SepiaCommand(null, "mytest2").run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidCommandSepia() {
    new SepiaCommand("mytest", "myTestNew").run(model);
  }

  @Test
  public void testNormalUsageSepia() {
    model.load(emptyImage, "mytest");
    new SepiaCommand("mytest", "mytest2").run(model);
    assertEquals(emptyImage, model.save("mytest2"));
  }

  @Test(expected = IllegalStateException.class)
  public void testNullImageNameSepiaMask() {
    model.load(emptyImage, "mytest");
    new SepiaCommand("mytest", null, "mytest").run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testNullNewImageNameSepiaMask() {
    model.load(emptyImage, "mytest");
    new SepiaCommand(null, "mytest", "mytest").run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidCommandSepiaMask() {
    new SepiaCommand("mytest", "myTestNew", "mask").run(model);
  }

  @Test
  public void testNormalUsageSepiaMask() {
    model.load(emptyImage, "mytest");
    new SepiaCommand("mytest", "mytest2", "mytest").run(model);
    assertEquals(emptyImage, model.save("mytest2"));
  }

  @Test(expected = IllegalStateException.class)
  public void testNullMaskImageNameSepia() {
    model.load(emptyImage, "mytest");
    new SepiaCommand("mytest", "mytest2", null).run(model);
  }

  // Sharpen

  @Test(expected = IllegalStateException.class)
  public void testNullImageNameSharpen() {
    model.load(emptyImage, "mytest");
    new SharpenCommand("mytest", null).run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testNullNewImageNameSharpen() {
    model.load(emptyImage, "mytest");
    new SharpenCommand(null, "mytest2").run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidCommandSharpen() {
    new SharpenCommand("mytest", "myTestNew").run(model);
  }

  @Test
  public void testNormalUsageSharpen() {
    model.load(emptyImage, "mytest");
    new SharpenCommand("mytest", "mytest2").run(model);
    assertEquals(emptyImage, model.save("mytest2"));
  }

  @Test(expected = IllegalStateException.class)
  public void testNullImageNameSharpenMask() {
    model.load(emptyImage, "mytest");
    new SharpenCommand("mytest", null, "mytest").run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testNullNewImageNameSharpenMask() {
    model.load(emptyImage, "mytest");
    new SharpenCommand(null, "mytest", "mytest").run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidCommandSharpenMask() {
    new SharpenCommand("mytest", "myTestNew", "mytest").run(model);
  }

  @Test
  public void testNormalUsageSharpenMask() {
    model.load(emptyImage, "mytest");
    new SharpenCommand("mytest", "mytest2", "mytest").run(model);
    assertEquals(emptyImage, model.save("mytest2"));
  }

  @Test(expected = IllegalStateException.class)
  public void testNullMaskImageNameSharpen() {
    model.load(emptyImage, "mytest");
    new SharpenCommand("mytest", "mytest2", null).run(model);
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

  @Test(expected = IllegalStateException.class)
  public void testNullNewImageNameGreyscaleMask() {
    model.load(emptyImage, "mytest");
    new GreyScale("mytest", null, "mytest", GreyscaleComponent.BLUE).run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testNullImageNameGreyscaleMask() {
    model.load(emptyImage, "mytest");
    new GreyScale(null, "mytest", "mytest", GreyscaleComponent.BLUE).run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testNullComponentGreyScaleMask() {
    model.load(emptyImage, "mytest");
    new GreyScale("mytest", "mytest", "mytest", null).run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidGreyscaleMask() {
    IIMEModel model = new IMEModel();
    new GreyScale("mytest", "mytest", "mytest", GreyscaleComponent.BLUE).run(model);
  }

  @Test
  public void testNormalUsageGreyscaleMask() {
    model.load(emptyImage, "mytest");
    for (GreyscaleComponent c : GreyscaleComponent.values()) {
      new GreyScale("mytest", "mytestGrey", "mytest", c).run(model);
      assertEquals(model.save("mytestGrey"), emptyImage);
    }
  }

  @Test(expected = IllegalStateException.class)
  public void testNullMaskImageNameGreyscale() {
    model.load(emptyImage, "mytest");
    new GreyScale("mytest", "mytest", null, GreyscaleComponent.BLUE).run(model);
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
    new Load("res/PNGImages/testOG.png", null).run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidLoad() {
    new Load("res/PPMImages/something.ppm", "mytest").run(model);
  }

  @Test
  public void testNormalPPMLoad() {
    new Load("res/PNGImages/testOG.png", "mytest").run(model);
    assertEquals(ImageUtil.readImage("res/PNGImages/testOG.png"), model.save("mytest"));
  }

  @Test
  public void testNormalPNGLoad() {
    new Load("res/PNGImages/testOG.png", "mytest").run(model);
    assertEquals(ImageUtil.readImage("res/PNGImages/testOG.png"), model.save("mytest"));
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
  public void testNormalPPMSave() {
    model.load(emptyImage, "mytest");
    new Save("test/test.ppm", "mytest").run(model);
    assertEquals(ImageUtil.readPPM("test/test.ppm"), emptyImage);
    if (!new File("test/test.ppm").delete()) {
      fail();
    }
  }

  @Test
  public void testNormalPNGSave() {
    IImage testImage = new Image(1, 1, new IPixel[][]{{new Pixel(255)}});
    model.load(testImage, "mytest");
    new Save("test/test.png", "mytest").run(model);
    assertEquals(testImage, ImageUtil.readImage("test/test.png"));
    if (!new File("test/test.png").delete()) {
      fail();
    }
  }

  @Test
  public void testNormalJPGSave() {
    IImage testImage = new Image(1, 1, new IPixel[][]{{new Pixel(255)}});
    model.load(testImage, "mytest");
    new Save("test/test.jpg", "mytest").run(model);
    assertEquals(testImage, ImageUtil.readImage("test/test.jpg"));
    if (!new File("test/test.jpg").delete()) {
      fail();
    }
  }

  // Downscale

  @Test(expected = IllegalStateException.class)
  public void testDownscaleLargeHeight() {
    IImage testImage = new Image(1, 1, new IPixel[][]{{new Pixel(255)}});
    model.load(testImage, "mytest");
    new Downscale("mytest", "mytest", 1, 2).run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testDownscaleLargeWidth() {
    IImage testImage = new Image(1, 1, new IPixel[][]{{new Pixel(255)}});
    model.load(testImage, "mytest");
    new Downscale("mytest", "mytest", 2, 1).run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testDownscaleLargeHeightWidth() {
    IImage testImage = new Image(1, 1, new IPixel[][]{{new Pixel(255)}});
    model.load(testImage, "mytest");
    new Downscale("mytest", "mytest", 2, 2).run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testDownscaleNegative() {
    model.load(emptyImage, "mytest");
    new Downscale("mytest", "mytest", -1, -1).run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testDownscaleNull() {
    model.load(emptyImage, "mytest");
    new Downscale(null, null, 0, 0).run(model);
  }

  @Test(expected = IllegalStateException.class)
  public void testDownscaleUnloaded() {
    new Downscale("mytest", "mytest", 0, 0).run(model);
  }

  @Test
  public void testDownscaleNormalUsage() {
    model.load(emptyImage, "mytest");
    new Downscale("mytest", "mytest", 0, 0).run(model);
    assertEquals(emptyImage, model.save("mytest"));
  }

}