import static model.ImageUtil.readFile;
import static org.junit.Assert.assertEquals;

import controller.ImageController;
import controller.ImageControllerImpl;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
  public void testLoading() throws FileNotFoundException {
    Reader in = new FileReader("res/4by4.ppm");
    StringBuffer out = new StringBuffer();
    ImageModel ppm = new RGBModel(readFile("res/4by4.ppm"));
    ImageView view = new RGBView(out);
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(view.toString(), "Loading...");
  }

  @Test
  public void testSaving() throws FileNotFoundException {
    Reader in = new FileReader("res/4by4.ppm");
    StringBuffer out = new StringBuffer();
    ImageModel ppm = new RGBModel(readFile("res/4by4.ppm"));
    ImageView view = new RGBView(out);
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(view.toString(), "Loading...");
  }

  @Test
  public void redTest() throws FileNotFoundException {
    Reader in = new FileReader("res/4by4.ppm make-red");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(out);
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "make red");
  }

  @Test
  public void greenTest() throws FileNotFoundException {
    Reader in = new FileReader("res/4by4.ppm make-green");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(out);
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "make green");
  }

  @Test
  public void blueTest() throws FileNotFoundException {
    Reader in = new FileReader("res/4by4.ppm make-blue");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(out);
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "make blue");
  }

  @Test
  public void valueTest() throws FileNotFoundException {
    Reader in = new FileReader("res/4by4.ppm value-component");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(out);
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "image value");
  }

  @Test
  public void intensityTest() throws FileNotFoundException {
    Reader in = new FileReader("res/4by4.ppm change intensity");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(out);
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "image intensity");
  }

  @Test
  public void lumaTest() throws FileNotFoundException {
    Reader in = new FileReader("res/4by4.ppm luma-component");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(out);
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "image luma");
  }

  @Test
  public void horizontalTest() throws FileNotFoundException {
    Reader in = new FileReader("res/4by4.ppm horizontal-flip");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(out);
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "flip horizontal");
  }

  @Test
  public void verticalTest() throws FileNotFoundException {
    Reader in = new FileReader("res/4by4.ppm vertical-flip");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(out);
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "flip vertical");
  }

  @Test
  public void brightenTest() throws FileNotFoundException {
    Reader in = new FileReader("res/4by4.ppm brighten 50");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(out);
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "brighten 50");
  }

  @Test
  public void darkenTest() throws FileNotFoundException {
    Reader in = new FileReader("res/4by4.ppm darken 50");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(out);
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "darken 50");
  }

  @Test
  public void blurTest() throws FileNotFoundException {
    Reader in = new FileReader("res/4by4.ppm blur");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(out);
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "gaussian blur");
  }

  @Test
  public void sharpenTest() throws FileNotFoundException {
    Reader in = new FileReader("res/4by4.ppm sharpen");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(out);
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "sharpen");
  }

  @Test
  public void sepiaTest() throws FileNotFoundException {
    Reader in = new FileReader("res/4by4.ppm sepia");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(out);
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "sepia");
  }

  @Test
  public void greyTest() throws FileNotFoundException {
    Reader in = new FileReader("res/4by4.ppm greyscale");
    StringBuilder out = new StringBuilder();
    MockModel ppm = new MockModel(out);
    ImageView view = new RGBView(out);
    ImageController test = new ImageControllerImpl(ppm, view, in);

    test.begin();

    assertEquals(out.toString(), "greyscale");
  }

}