import static model.ImageUtil.readFile;
import static org.junit.Assert.assertEquals;

import controller.ImageController;
import controller.ImageControllerImpl;
import java.io.Reader;
import java.io.StringReader;
import model.ImageModel;
import model.MockModel;
import model.RGBModel;
import org.junit.Before;
import org.junit.Test;
import view.ImageView;
import view.RGBView;


/**
 * Represents the tests for the ImageControllerImpl class. Ensures that all methods and constructors
 * work properly.
 */
public class ImageControllerImplTest {

  private final String menu = "Instructions:\nTo load an image type: load filepath\n"
      + "To save an image type: save filepath\n"
      + "To show the red component of an image type: make-red filepath\n"
      + "To show the blue component of an image type: make-blue filepath\n"
      + "To show the green component of an image type: make-green filepath\n"
      + "To show the value component of an image type: value-component filepath\n"
      + "To blur an image type: blur filepath\n"
      + "To sharpen an image type: sharpen filepath\n"
      + "To make an image sepia type: sepia filepath\n"
      + "To make an image greyscale type: greyscale filepath\n"
      + "To do a horizontal flip type: horizontal-flip filepath\n"
      + "To do a vertical flip type: vertical-flip filepath\n"
      + "To brighten an image type: brighten amount\n"
      + "To darken an image type: darken amount\n"
      + "To quit the program type: quit or q\n"
      + "For example, to load kick.ppm located in the res folder type: \n";
  ImageModel flower;
  StringBuilder append;
  ImageView view;
  Reader in;

  @Before
  public void setUp() {
    flower = new RGBModel(readFile("res/flower.ppm"));
    append = new StringBuilder();
    view = new RGBView(append);
    in = new StringReader("");
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructFail() {
    this.setUp();
    new ImageControllerImpl(null, view, in);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructFail2() {
    this.setUp();
    new ImageControllerImpl(flower, null, in);
  }

  @Test(expected = IllegalArgumentException.class)
  public void constructFail3() {
    this.setUp();
    new ImageControllerImpl(flower, view, null);
  }

  @Test
  public void testLoading() {
    Reader in = new StringReader("load res/4by4.ppm\nq");
    StringBuffer out = new StringBuffer();
    ImageModel ppm = new RGBModel();
    ImageView view = new RGBView(out);
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), menu + "load res/kick.ppm\nInsert a valid command: \nLoading...."
        + ".\nQuitting.....\n");
  }

  @Test
  public void testSaving() {
    Reader in = new StringReader("load res/4by4.ppm\nsave res/4by4.ppm\nq");
    StringBuffer out = new StringBuffer();
    ImageModel ppm = new RGBModel();
    ImageView view = new RGBView(out);
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), menu
        + "load res/kick.ppm\nInsert a valid command: \nLoading.....\nSaving.....\nQuitting.....\n");
  }

  @Test
  public void redTest() {
    Reader in = new StringReader("make-red res/4by4.ppm\nq");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(new StringBuilder());
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "make red");
  }

  @Test
  public void greenTest() {
    Reader in = new StringReader("make-green res/4by4.ppm\nq");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(new StringBuilder());
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "make green");
  }

  @Test
  public void blueTest() {
    Reader in = new StringReader("make-blue res/4by4.ppm\nq");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(new StringBuilder());
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "make blue");
  }

  @Test
  public void valueTest() {
    Reader in = new StringReader("value-component res/4by4.ppm\nq");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(new StringBuilder());
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "image value");
  }

  @Test
  public void intensityTest() {
    Reader in = new StringReader("change-intensity res/4by4.ppm\nq");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(new StringBuilder());
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "image intensity");
  }

  @Test
  public void lumaTest() {
    Reader in = new StringReader("luma-component res/4by4.ppm\nq");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(new StringBuilder());
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "image luma");
  }

  @Test
  public void horizontalTest() {
    Reader in = new StringReader("horizontal-flip res/4by4.ppm\nq");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(new StringBuilder());
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "flip horizontal");
  }

  @Test
  public void verticalTest() {
    Reader in = new StringReader("vertical-flip res/4by4.ppm\nq");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(new StringBuilder());
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "flip vertical");
  }

  @Test
  public void brightenTest() {
    Reader in = new StringReader("load res/4by4.ppm\nbrighten 50\nq");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(new StringBuilder());
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "brighten 50");
  }

  @Test
  public void darkenTest() {
    Reader in = new StringReader("load res/4by4.ppm\ndarken 50\nq");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(new StringBuilder());
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "darken 50");
  }

  @Test
  public void blurTest() {
    Reader in = new StringReader("blur res/4by4.ppm\nq");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(new StringBuilder());
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "gaussian blur");
  }

  @Test
  public void sharpenTest() {
    Reader in = new StringReader("sharpen res/4by4.ppm\nq");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(new StringBuilder());
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "sharpen");
  }

  @Test
  public void sepiaTest() {
    Reader in = new StringReader("sepia res/4by4.ppm\nq");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(new StringBuilder());
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "sepia");
  }

  @Test
  public void greyTest() {
    Reader in = new StringReader("greyscale res/4by4.ppm\nq");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(new StringBuilder());
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "greyscale");
  }
}