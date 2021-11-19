package cs3500.ime;

import cs3500.ime.controller.GuiController;
import cs3500.ime.controller.IGuiController;
import cs3500.ime.controller.IMEController;
import cs3500.ime.model.IIMEModel;
import cs3500.ime.model.IMEModel;
import cs3500.ime.view.GuiView;
import cs3500.ime.view.IGuiView;
import cs3500.ime.view.IMETextView;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

/**
 * Main class for this IME program.
 */
public class IME {


  /**
   * Instantiates the program using Commands.txt as an input file.
   */
  public static void main(String[] args) throws FileNotFoundException {
    if (args.length > 0) {
      if (args[0].equals("-text")) {
        new IMEController(new IMEModel(), new IMETextView(System.out),
            new InputStreamReader(System.in)).run();
      } else if (args[0].equals("-file")) {
        if (args.length != 2) {
          System.out.println("File not specified.\n");
        } else {
          new IMEController(new IMEModel(), new IMETextView(System.out),
              new InputStreamReader(new FileInputStream(args[1]))).run();
        }
      } else {
        System.out.println("Invalid flag.");
      }
    } else {
      IIMEModel model = new IMEModel();
      IGuiView view = new GuiView();
      IGuiController cont = new GuiController(model, view);
      view.acceptFeatures(cont);
    }
  }
}
