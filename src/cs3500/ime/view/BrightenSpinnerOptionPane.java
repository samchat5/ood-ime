package cs3500.ime.view;

/**
 * Option pane used for getting the amount a user wants to brighten the image by in our GUI. Shown
 * as a dialog box with a spinner, clamped to the range [-255, 255]. Closing or cancelling the
 * dialog does nothing, no matter what value the spinner was at that point.
 */
public class BrightenSpinnerOptionPane extends SpinnerOptionPane {

  /**
   * Constructor for this brighten option pane.
   */
  public BrightenSpinnerOptionPane() {
    super("How much do you want to brighten by?", "Brighten", -255, 255, 0, 1);
  }
}