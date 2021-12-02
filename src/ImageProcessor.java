import controller.GuiControllerImpl;
import controller.ImageController;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import controller.ImageControllerImpl;
import java.io.Reader;
import model.ImageModel;
import model.RGBModel;
import view.GuiView;
import view.GuiViewImpl;
import view.ImageView;
import view.RGBView;

/**
 * Represents the actual image processor.
 * Allows the user to start using the image processor by initializing model, view and controller
 * and starting the program.
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
    ImageModel model = new RGBModel();
    ImageController controller;
    InputStream inputStream = System.in;
    Appendable out = System.out;
    Readable in = new BufferedReader(new InputStreamReader(inputStream));

    if (args.length > 2) {
      if (args[0].equals("java") && args[1].equals("-jar")
          && args[2].equals("Image-Processor.jar")) {
        if (args.length == 5) {
          if (args[3].equals("-file")) {  //for script.txt
            try {
              Reader input = new FileReader(args[4]);
              view = new RGBView(out);
              controller = new ImageControllerImpl(model, view, input);
              controller.begin();
            } catch (IllegalArgumentException i) {
              throw new IllegalArgumentException("file name is not valid");
            }
          }
        } else if (args.length == 4) {
          if (args[3].equals("-text")) { //opens terminal
            try {
              view = new RGBView(out);
              controller = new ImageControllerImpl(model, view, in);
              controller.begin();
            } catch (IllegalArgumentException i) {
              throw new IllegalArgumentException("file name is not valid");
            }
          }
        } else {
          try {
            view2 = new GuiViewImpl(model);
            controller = new GuiControllerImpl(model, view2);
            controller.begin();
          } catch (IllegalArgumentException i) {
            throw new IllegalArgumentException("file name is not valid");
          }
        }
      } else {
        throw new IllegalArgumentException("Error: Invalid command given.");
      }

    }
  }
}
