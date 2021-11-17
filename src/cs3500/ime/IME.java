package cs3500.ime;

import cs3500.ime.controller.GuiController;
import cs3500.ime.controller.IGuiController;
import cs3500.ime.model.IIMEModel;
import cs3500.ime.model.IMEModel;
import cs3500.ime.view.GuiView;
import cs3500.ime.view.IGuiView;

/**
 * Main class for this IME program.
 */
public class IME {

  /**
   * Instantiates the program using Commands.txt as an input file.
   *
   * @param args unused
   */
  public static void main(String[] args) {
    /*
    FileInputStream commands = args.length > 0 ? new FileInputStream(args[0]) :
        new FileInputStream("Commands.txt");
    IIMEController cont = new IMEController(new IMEModel(), new IMETextView(System.out),
        new InputStreamReader(commands));
    cont.run();
    */
    IIMEModel model = new IMEModel();
    IGuiView view = new GuiView(model);
    IGuiController cont = new GuiController(model, view);

    view.acceptFeatures(cont);
    cont.run();
  }
}
