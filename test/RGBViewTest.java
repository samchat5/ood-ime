import static model.ImageUtil.readFile;
import static org.junit.Assert.assertEquals;

import model.ImageModel;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import model.RGBModel;
import view.MockView;
import view.RGBView;

/**
 * Represents the tests for the RGBView class.
 * Ensures that all methods and constructors are working as they are supposed to as defined by their
 * descriptions. Ensures that all visualizations are accurate.
 */
public class RGBViewTest {

  ImageModel flower;
  StringBuilder append;
  RGBView view;


  /**
   * Initializes values for testing.
   * Ensures that mutation will not disrupt any tests.
   */
  @Before
  public void setUp() {
    flower = new RGBModel(readFile("res/flower.ppm"));
    append = new StringBuilder();
    view = new RGBView(append);
  }

  @Test
  public void constructorTest() throws IOException {
    this.setUp();
    assertEquals(append.toString(), "");
    assertEquals(view.toString(),"");
    view.renderMessage(view.toString());
    assertEquals(append.toString(), "");
  }


  @Test (expected = IllegalArgumentException.class)
  public void constructFail2() {
    RGBView nullFail = new RGBView(null);
  }

  @Test
  public void toStringTest()  {
    this.setUp();
    try {
      view.renderMessage("hii");
    } catch (IOException e) {
      e.printStackTrace();
    }
    assertEquals(view.toString(), "hii");
  }

  @Test
  public void renderMessageTest() throws IOException {
    this.setUp();
    view.renderMessage("hi");
    assertEquals(append.toString(), "hi\n");
    view.renderMessage(" bye");
    assertEquals(append.toString(), "hi\n bye\n");
  }

  @Test (expected = IOException.class)
  public void IOTest() throws IOException {
    MockView mock = new MockView(append);
    mock.renderMessage("exception test");
  }

}