package cs3500.ime.controller;

import cs3500.ime.controller.commands.BlurCommand;
import cs3500.ime.controller.commands.Brighten;
import cs3500.ime.controller.commands.Downscale;
import cs3500.ime.controller.commands.GreyScale;
import cs3500.ime.controller.commands.HorizontalFlip;
import cs3500.ime.controller.commands.Load;
import cs3500.ime.controller.commands.Save;
import cs3500.ime.controller.commands.SepiaCommand;
import cs3500.ime.controller.commands.SharpenCommand;
import cs3500.ime.controller.commands.VerticalFlip;
import cs3500.ime.model.GreyscaleComponent;
import cs3500.ime.model.IIMEModel;
import cs3500.ime.model.image.ImageUtil;
import cs3500.ime.view.IGuiView;
import java.util.Scanner;
import java.util.Stack;

/**
 * Concrete class from our GUI controller. Similar to IMEController, but doesn't take in a readable,
 * and the known commands don't need arguments for the image names, since only one image is being
 * operated on at a time.
 */
public class GuiController extends AIMEController implements IGuiController {

  /**
   * Constructor for this GuiController.
   *
   * @param model the model object to call commands on
   * @param view  the view that this controller takes input from
   * @throws IllegalArgumentException if the parameters are null
   */
  public GuiController(IIMEModel model, IGuiView view)
      throws IllegalArgumentException {
    if (model == null || view == null) {
      throw new IllegalArgumentException("Null model or view.");
    }
    this.model = model;
    this.view = view;
    this.commands = new Stack<>();

    knownCommands.put("brighten", (Scanner s) -> new Brighten(s.nextInt(), "image", "image"));
    knownCommands.put("vertical-flip", (Scanner s) -> new VerticalFlip("image", "image"));
    knownCommands.put("horizontal-flip", (Scanner s) -> new HorizontalFlip("image", "image"));
    knownCommands.put("value-component",
        (Scanner s) -> new GreyScale("image", "image", GreyscaleComponent.VALUE));
    knownCommands.put("luma-component",
        (Scanner s) -> new GreyScale("image", "image", GreyscaleComponent.LUMA));
    knownCommands.put("intensity-component",
        (Scanner s) -> new GreyScale("image", "image", GreyscaleComponent.INTENSITY));
    knownCommands.put("red-component",
        (Scanner s) -> new GreyScale("image", "image", GreyscaleComponent.RED));
    knownCommands.put("blue-component",
        (Scanner s) -> new GreyScale("image", "image", GreyscaleComponent.BLUE));
    knownCommands.put("green-component",
        (Scanner s) -> new GreyScale("image", "image", GreyscaleComponent.GREEN));
    knownCommands.put("sepia", (Scanner s) -> new SepiaCommand("image", "image"));
    knownCommands.put("sharpen", (Scanner s) -> new SharpenCommand("image", "image"));
    knownCommands.put("blur", (Scanner s) -> new BlurCommand("image", "image"));
    knownCommands.put("load", (Scanner s) -> new Load(s.nextLine().strip(), "image"));
    knownCommands.put("save", (Scanner s) -> new Save(s.nextLine().strip(), "image"));
    knownCommands.put("downscale", (Scanner s) -> new Downscale("image", "image", s.nextInt(),
        s.nextInt()));
  }

  @Override
  public void run() throws IllegalStateException {
    super.run();
    ((IGuiView) this.view).loadImage(ImageUtil.getBufferedImage(model.save("image")));
  }

  @Override
  public void setScanner(Scanner s) throws IllegalArgumentException {
    if (s == null) {
      throw new IllegalArgumentException("Null scanner.");
    }
    this.scanner = s;
  }
}