package cs3500.ime.view;

import java.io.IOException;

public class IMETextView implements IIMEView {

  private final Appendable app;

  public IMETextView(Appendable app) {
    if (app == null) {
      throw new IllegalArgumentException("Null appendable.");
    }
    this.app = app;
  }

  @Override
  public void displayMessage(String message) throws IOException {
    app.append(message);
  }
}
