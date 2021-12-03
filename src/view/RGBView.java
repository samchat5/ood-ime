package view;

import java.io.IOException;

/**
 * This represents the view of an RGB image. Implements the ImageView interface.
 */
public class RGBView implements ImageView {

  private final Appendable append;

  /**
   * This constructor creates a RGBView with the model and appendable as the inputs.
   *
   * @param append the appendable.
   * @throws IllegalArgumentException when state or appendable is null.
   */
  public RGBView(Appendable append) throws IllegalArgumentException {
    if (append == null) {
      throw new IllegalArgumentException("Appendable cannot be null");
    }
    this.append = append;
  }

  /**
   * Renders the given message. Sends the string to the provided output.
   *
   * @param message a String representing the message to be shared.
   */
  @Override
  public void renderMessage(String message) throws IOException {
    if (message == null) {
      throw new IllegalArgumentException("message cannot be null");
    }
    this.append.append(message).append("\n");
  }
}
