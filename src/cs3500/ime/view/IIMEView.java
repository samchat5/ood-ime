package cs3500.ime.view;

import java.io.IOException;

/**
 * Interface for the view of the IME program. Concrete classes can be anywhere from simple text
 * views to a full GUI.
 */
public interface IIMEView {

  /**
   * Display the given message to the user.
   *
   * @param message message to display
   * @throws IOException if the message fails to transmit
   */
  void displayMessage(String message) throws IOException;
}
