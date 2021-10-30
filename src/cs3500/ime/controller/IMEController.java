package cs3500.ime.controller;

import cs3500.ime.GreyscaleComponent;
import cs3500.ime.controller.commands.Brighten;
import cs3500.ime.controller.commands.GreyScale;
import cs3500.ime.controller.commands.HorizontalFlip;
import cs3500.ime.controller.commands.IIMECommand;
import cs3500.ime.controller.commands.Load;
import cs3500.ime.controller.commands.Save;
import cs3500.ime.controller.commands.VerticalFlip;
import cs3500.ime.model.IIMEModel;
import cs3500.ime.view.IIMEView;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Function;

public class IMEController implements IIMEController {

  private final IIMEModel model;
  private final IIMEView view;
  private final Stack<IIMECommand> commands;
  private final Readable readable;

  private final Map<String, Function<Scanner, IIMECommand>> knownCommands;

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
    this.readable = readable;
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
  }

  /**
   * Runs the controller and starts up the main IME program.
   *
   * @throws IllegalStateException iff the controller is unable to successfully read input or
   *                               transmit output
   */
  @Override
  public void run() throws IllegalStateException {

  }
}