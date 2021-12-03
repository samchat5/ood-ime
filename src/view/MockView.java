package view;

import java.io.IOException;


/**
 * Represents a mock class of ImageView. Class contains simplified methods for use in testing that
 * the controller communicates accurately.
 */
public class MockView implements ImageView {

  /**
   * Represents the constructor for the MockView class. Initialize values given by the constructor.
   *
   * @param append represents the place outputs are written to.
   * @throws IllegalArgumentException if either given value is null.
   */
  public MockView(Appendable append) throws IllegalArgumentException {
    if (append == null) {
      throw new IllegalArgumentException();
    }
  }

  /**
   * Renders the given message. Sends the string to the provided output.
   *
   * @param message a String representing the message to be shared.
   */
  @Override
  public void renderMessage(String message) throws IOException {
    throw new IOException();
  }
}
