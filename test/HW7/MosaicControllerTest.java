package HW7;

import static junit.framework.TestCase.assertEquals;

import controller.GuiControllerImpl;
import controller.HW7.Mosaic;
import controller.HW7.MosaicController;
import controller.HW7.MosaicGuiController;
import controller.ImageController;
import java.io.StringReader;
import model.HW7.MockMosaicModel;
import model.HW7.MosaicModel;
import model.HW7.MosaicModelImpl;
import org.junit.Test;
import view.HW7.MosaicGuiView;
import view.RGBView;

public class MosaicControllerTest {

  private final String menu = "Instructions:\nTo load an image type: load filepath\nTo save an "
      + "image type: save filepath\nTo show the red component of an image type: make-red "
      + "filepath\nTo show the blue component of an image type: make-blue filepath\nTo show the "
      + "green component of an image type: make-green filepath\nTo show the value component of an "
      + "image type: value-component filepath\nTo blur an image type: blur filepath\nTo sharpen an"
      + " image type: sharpen filepath\nTo make an image sepia type: sepia filepath\nTo make an "
      + "image greyscale type: greyscale filepath\nTo do a horizontal flip type: horizontal-flip "
      + "filepath\nTo do a vertical flip type: vertical-flip filepath\nTo brighten an image type: "
      + "brighten amount\nTo darken an image type: darken amount\nTo mosaic an image type: mosaic "
      + "seed-count\nTo quit the program type: quit or q\nFor example, to load kick.ppm located in "
      + "the res folder type: \nload res/kick.ppm\nInsert a valid command: \n";
  private final String loading = "Loading.....\n";
  private final String quitting = "\nQuitting.....\n";
  private final String mosaicking = "Mosaicking...\n";

  @Test(expected = IllegalArgumentException.class)
  public void testMosaicCommandNullModel() {
    new Mosaic(0, 0, null, null);
  }

  @Test
  public void testMosaicFileNotFound() {
    StringBuilder app = new StringBuilder();
    String in = "load tst.png\nmosaic 1000\nquit\n";
    ImageController cont = new MosaicController(new MosaicModelImpl(), new RGBView(app),
        new StringReader(in));
    cont.begin();
    assertEquals(app.toString(),
        menu + loading + "Invalid input \nload is not a valid input. " + "Please re-enter: \n\n"
            + mosaicking + "Mosaic failed: Invalid image (either not loaded" + " or corrupted).\n"
            + quitting);
  }

  @Test
  public void testMosaicInvalidArgs() {
    StringBuilder app = new StringBuilder();
    String in = "load rest/testOG.png\nmosaic askdjh1198\nquit\n";
    ImageController cont = new MosaicController(new MosaicModelImpl(), new RGBView(app),
        new StringReader(in));
    cont.begin();
    assertEquals(app.toString(), menu + loading + "Invalid input \nload is not a valid input. "
        + "Please re-enter: \n\nInvalid input \nmosaic is not a valid input. Please re-enter: \n"
        + quitting);
  }

  @Test
  public void testInvalidMosaic() {
    StringBuilder app = new StringBuilder();
    String in = "mosaic 1000\nquit\n";
    ImageController cont = new MosaicController(new MosaicModelImpl(), new RGBView(app),
        new StringReader(in));
    cont.begin();
    assertEquals(app.toString(), menu + mosaicking + "Mosaic failed: Invalid image (either not "
        + "loaded or "
        + "corrupted).\n" + quitting);
  }

  @Test
  public void testNegativeMosaic() {
    StringBuilder app = new StringBuilder();
    String in = "load res/testOG.png\nmosaic -50\nquit\n";
    ImageController cont = new MosaicController(new MosaicModelImpl(), new RGBView(app),
        new StringReader(in));
    cont.begin();
    assertEquals(app.toString(),
        menu + loading + mosaicking + "Mosaic failed: Seed count must be greater than 0.\n"
            + quitting);
  }

  @Test
  public void testZeroMosaic() {
    StringBuilder app = new StringBuilder();
    String in = "load res/testOG.png\nmosaic 0\nquit\n";
    ImageController cont = new MosaicController(new MosaicModelImpl(), new RGBView(app),
        new StringReader(in));
    cont.begin();
    assertEquals(app.toString(),
        menu + loading + mosaicking + "Mosaic failed: Seed count must be greater than 0.\n"
            + quitting);
  }

  @Test
  public void testNormalUsageMosaic() {
    StringBuilder app = new StringBuilder();
    String in = "load res/testOG.png\nmosaic 1000\nquit\n";
    ImageController cont = new MosaicController(new MockMosaicModel(app),
        new RGBView(new StringBuilder()), new StringReader(in));
    cont.begin();
    assertEquals(app.toString(), "mosaic 1000");
  }

  @Test
  public void testGuiController() {
    StringBuilder app = new StringBuilder();
    String in = "load res/testOG.png\nmosaic 1000\n";
    MosaicModel model = new MockMosaicModel(app);
    GuiControllerImpl cont = new MosaicGuiController(model,
        new MosaicGuiView(model));
    cont.begin();
    cont.accept(in);
    assertEquals(app.toString(), "mosaic 1000");
  }

  // Unable to test invalid mosaic values for the GUI controller, since errors are detected
  // in the controller, and shown through a dialog, which we can't test. But, the GUI clamps the
  // mosaic values anyway, so it's impossible to enter invalid values.
}
