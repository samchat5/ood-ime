package cs3500.ime;

import cs3500.ime.controller.IIMEController;
import cs3500.ime.controller.IMEController;
import cs3500.ime.model.IMEModel;
import cs3500.ime.view.IMETextView;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Main class for this IME program.
 */
public class IME {

  /**
   * Instantiates the program using Commands.txt as an input file.
   *
   * @param args unused
   */
  public static void main(String[] args) throws IOException {
    FileInputStream commands = args.length > 0 ? new FileInputStream(args[0]) :
        new FileInputStream("Commands.txt");
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(System.out),
        new InputStreamReader(commands));
    cont.run();
  }
}
