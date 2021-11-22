package cs3500.ime.controller;

import cs3500.ime.controller.commands.BlurCommand;
import cs3500.ime.controller.commands.Brighten;
import cs3500.ime.controller.commands.GreyScale;
import cs3500.ime.controller.commands.HorizontalFlip;
import cs3500.ime.controller.commands.Load;
import cs3500.ime.controller.commands.LumaTransform;
import cs3500.ime.controller.commands.Save;
import cs3500.ime.controller.commands.SepiaCommand;
import cs3500.ime.controller.commands.SharpenCommand;
import cs3500.ime.controller.commands.VerticalFlip;
import cs3500.ime.model.GreyscaleComponent;
import cs3500.ime.model.IIMEModel;
import cs3500.ime.view.IIMEView;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Stack;

/**
 * Concrete implementation of {@code IIMEController}. Uses the command design pattern, with a map of
 * known commands initialized on construction, and a stack to be used for undoing operations in the
 * future/maintaining history.
 */
public class IMEController extends AIMEController implements IIMEController {

  /**
   * Constructor for this controller that takes in a model to which it sends input from the user.
   *
   * @param model    model that this controller uses.
   * @param view     the view that this controller uses.
   * @param readable the input stream
   * @throws IllegalArgumentException if model is null
   */
  public IMEController(IIMEModel model, IIMEView view, Readable readable)
      throws IllegalArgumentException {
    if (model == null || view == null || readable == null) {
      throw new IllegalArgumentException("Null model, readable or view.");
    }
    this.model = model;
    this.scanner = new Scanner(readable);
    this.view = view;
    this.commands = new Stack<>();
    this.knownCommands = new HashMap<>();
    knownCommands.put("load", (Scanner s) -> new Load(s.next(), s.next()));
    knownCommands.put("save", (Scanner s) -> new Save(s.next(), s.next()));
    knownCommands.put("brighten", (Scanner s) -> new Brighten(s.nextInt(), s.next(), s.next()));
    knownCommands.put("vertical-flip", (Scanner s) -> new VerticalFlip(s.next(), s.next()));
    knownCommands.put("horizontal-flip", (Scanner s) -> new HorizontalFlip(s.next(), s.next()));
    knownCommands.put("value-component",
        (Scanner s) -> new GreyScale(s.next(), s.next(), GreyscaleComponent.VALUE));
    knownCommands.put("luma-component",
        (Scanner s) -> new GreyScale(s.next(), s.next(), GreyscaleComponent.LUMA));
    knownCommands.put("intensity-component",
        (Scanner s) -> new GreyScale(s.next(), s.next(), GreyscaleComponent.INTENSITY));
    knownCommands.put("red-component",
        (Scanner s) -> new GreyScale(s.next(), s.next(), GreyscaleComponent.RED));
    knownCommands.put("blue-component",
        (Scanner s) -> new GreyScale(s.next(), s.next(), GreyscaleComponent.BLUE));
    knownCommands.put("green-component",
        (Scanner s) -> new GreyScale(s.next(), s.next(), GreyscaleComponent.GREEN));
    knownCommands.put("sepia", (Scanner s) -> new SepiaCommand(s.next(), s.next()));
    knownCommands.put("sharpen", (Scanner s) -> new SharpenCommand(s.next(), s.next()));
    knownCommands.put("blur", (Scanner s) -> new BlurCommand(s.next(), s.next()));
    knownCommands.put("greyscale", (Scanner s) -> new LumaTransform(s.next(), s.next()));
  }

  /**
   * Runs the controller and starts up the main IME program.
   *
   * @throws IllegalStateException iff the controller is unable to successfully read input or
   *                               transmit output, or it runs out of inputs
   */
  @Override
  public void run() throws IllegalStateException {
    super.run();
    if (!this.hasQuit) {
      throw new IllegalStateException();
    }
  }
}
