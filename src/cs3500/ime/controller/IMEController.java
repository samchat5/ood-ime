package cs3500.ime.controller;

import cs3500.ime.controller.commands.BlurCommand;
import cs3500.ime.controller.commands.Brighten;
import cs3500.ime.controller.commands.Downscale;
import cs3500.ime.controller.commands.GreyScale;
import cs3500.ime.controller.commands.HorizontalFlip;
import cs3500.ime.controller.commands.IIMECommand;
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
import java.util.function.BiFunction;
import java.util.function.Function;

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
        (Scanner s) -> greyScaleCallback(s, GreyscaleComponent.VALUE));
    knownCommands.put("luma-component",
        (Scanner s) -> greyScaleCallback(s, GreyscaleComponent.LUMA));
    knownCommands.put("intensity-component",
        (Scanner s) -> greyScaleCallback(s, GreyscaleComponent.INTENSITY));
    knownCommands.put("red-component", (Scanner s) -> greyScaleCallback(s, GreyscaleComponent.RED));
    knownCommands.put("blue-component",
        (Scanner s) -> greyScaleCallback(s, GreyscaleComponent.BLUE));
    knownCommands.put("green-component",
        (Scanner s) -> greyScaleCallback(s, GreyscaleComponent.GREEN));
    knownCommands.put("sepia", (Scanner s) -> maskCommandCallback(s, SepiaCommand::new,
        (String s1) -> (String s2, String s3) -> new SepiaCommand(s1, s2, s3)));
    knownCommands.put("sharpen", (Scanner s) -> maskCommandCallback(s, SharpenCommand::new,
        (String s1) -> (String s2, String s3) -> new SharpenCommand(s1, s2, s3)));
    knownCommands.put("blur", (Scanner s) -> maskCommandCallback(s, BlurCommand::new,
        (String s1) -> (String s2, String s3) -> new BlurCommand(s1, s2, s3)));
    knownCommands.put("greyscale", (Scanner s) -> maskCommandCallback(s, LumaTransform::new,
        (String s1) -> (String s2, String s3) -> new LumaTransform(s1, s2, s3)));
    knownCommands.put("downscale",
        (Scanner s) -> new Downscale(s.next(), s.next(), s.nextInt(), s.nextInt()));
  }

  private IIMECommand maskCommandCallback(Scanner s,
      BiFunction<String, String, IIMECommand> regConstructor,
      Function<String, BiFunction<String, String, IIMECommand>> maskConstructor) {
    String[] args = scanner.nextLine().split(" ");
    return args.length == 3 ? regConstructor.apply(args[1], args[2])
        : maskConstructor.apply(args[1]).apply(args[3], args[2]);
  }

  private IIMECommand greyScaleCallback(Scanner s, GreyscaleComponent component) {
    return maskCommandCallback(s, (String s1, String s2) -> new GreyScale(s1, s2, component),
        (String s1) -> (String s2, String s3) -> new GreyScale(s1, s2, s3, component));
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
