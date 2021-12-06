import controller.HW7.MosaicController;
import controller.HW7.MosaicGuiController;
import controller.ImageController;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import model.HW7.MosaicModel;
import model.HW7.MosaicModelImpl;
import view.GuiView;
import view.HW7.MosaicGuiView;
import view.ImageView;
import view.RGBView;

/**
 * Represents the actual image processor. Allows the user to start using the image processor by
 * initializing model, view and controller and starting the program.
 */
public final class ImageProcessor {

  /**
   * Represents the method used to run the image processor. Initializes the model, view, and
   * controller based of the user inputs and starts the process.
   *
   * @param args represents the input used to determine the name of the file to open.
   */
  public static void main(String[] args) throws FileNotFoundException {
    ImageView view;
    GuiView view2;
    MosaicModel model = new MosaicModelImpl();
    ImageController controller;
    InputStream inputStream = System.in;
    Appendable out = System.out;
    Readable in = new BufferedReader(new InputStreamReader(inputStream));

    if (args.length == 2) {
      if (args[0].equals("-file")) {  // for script.txt
        try {
          Reader input = new FileReader(args[1]);
          view = new RGBView(out);
          controller = new MosaicController(model, view, input);
          controller.begin();
        } catch (IllegalArgumentException i) {
          throw new IllegalArgumentException("file name is not valid");
        }
      }
    } else if (args.length == 1) {
      if (args[0].equals("-text")) { // opens terminal
        try {
          view = new RGBView(out);
          controller = new MosaicController(model, view, in);
          controller.begin();
        } catch (IllegalArgumentException i) {
          throw new IllegalArgumentException("file name is not valid");
        }
      }
    } else if (args.length == 0) { // opens gui
      try {
        view2 = new MosaicGuiView(model);
        controller = new MosaicGuiController(model, view2);
        controller.begin();
      } catch (IllegalArgumentException i) {
        throw new IllegalArgumentException("file name is not valid");
      }
    } else {
      throw new IllegalArgumentException("Invalid arguments");
    }
  }
}
