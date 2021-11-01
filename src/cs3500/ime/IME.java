package cs3500.ime;

import cs3500.ime.controller.IIMEController;
import cs3500.ime.controller.IMEController;
import cs3500.ime.model.IMEModel;
import cs3500.ime.view.IMETextView;
import java.io.InputStreamReader;

/**
 * Main class for this IME program.
 */
public class IME {

  /**
   * Instantiates the program using stdio.
   *
   * @param args unused
   */
  public static void main(String[] args) {
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(System.out),
        new InputStreamReader(System.in));
    cont.run();
  }
}
