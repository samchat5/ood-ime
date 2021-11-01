package cs3500.ime.view;

import java.io.IOException;

public class IMETextView implements IIMEView {

  private final Appendable app;

  /**
   * Constructor for this text view, which only takes in an appendable to write to.
   *
   * @param app appendable object
   * @throws IllegalArgumentException if the appendable is null
   */
  public IMETextView(Appendable app) throws IllegalArgumentException {
    if (app == null) {
      throw new IllegalArgumentException("Null appendable.");
    }
    this.app = app;
  }

  /**
   * Transmits the given message to the appendable.
   *
   * @param message message to display
   * @throws IOException if the transmission fails
   */
  @Override
  public void displayMessage(String message) throws IOException {
    app.append(message);
  }
}
