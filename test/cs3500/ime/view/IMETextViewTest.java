package cs3500.ime.view;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import cs3500.ime.controller.MockAppendable;
import java.io.IOException;
import org.junit.Test;

/**
 * Tests for IMETextView.
 */
public class IMETextViewTest {

  @Test
  public void testDisplayMessage() {
    try {
      StringBuilder in = new StringBuilder();
      IIMEView test = new IMETextView(in);
      test.displayMessage("testing testing 123");
      assertEquals(in.toString(), "testing testing 123");
    } catch (IOException e) {
      fail();
    }
  }

  @Test(expected = IOException.class)
  public void testDisplayMessageIOException() throws IOException {
    Appendable in = new MockAppendable();
    IIMEView test = new IMETextView(in);
    test.displayMessage("testing testing 123");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    new IMETextView(null);
  }
}