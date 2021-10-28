package cs3500.ime.controller;

/**
 * Represents a Controller for the IME program. Passes user input to the model and view for the
 * program, and makes sure the user enters proper input. Only has one public method ({@code run ()})
 * that starts the game. Concrete classes may add their own private methods.
 */
public interface IIMEController {

  /**
   * Runs the controller and starts up the main IME program.
   *
   * @throws IllegalStateException iff the controller is unable to successfully read input or
   *                               transmit output
   */
  void run() throws IllegalStateException;
}
