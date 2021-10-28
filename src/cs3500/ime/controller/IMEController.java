package cs3500.ime.controller;

import cs3500.ime.controller.commands.IIMECommand;
import cs3500.ime.model.IIMEModel;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Function;

public class IMEController implements IIMEController {

  private final IIMEModel model;
  private final Stack<IIMECommand> commands;
  private final Map<String, Function<Scanner, IIMECommand>> knownCommands;

  /**
   * Constructor for this controller that takes in a model to which it sends input from the user.
   *
   * @param model model that this controller uses.
   */
  public IMEController(IIMEModel model) {
    this.model = model;
    this.commands = new Stack<>();
    this.knownCommands = new HashMap<>();
    // knownCommands.put("load", (Scanner s) -> { return new Load(...); })
    // knownCommands.put("save", (Scanner s) -> { return new Save(...); })
    // knownCommands.put("brighten", (Scanner s) -> { return new Brighten(...); })
    // knownCommands.put("vertical-flip", (Scanner s) -> { return new VerticalFlip(...); })
    // knownCommands.put("horizontal-flip", (Scanner s) -> { return new HorizontalFlip(...); })
    // knownCommands.put("value-component", (Scanner s) -> { return new Greyscale(...); })
    // knownCommands.put("luma-component", (Scanner s) -> { return new Greyscale(...); })
    // knownCommands.put("intensity-component", (Scanner s) -> { return new Greyscale(...); })
    // knownCommands.put("red-component", (Scanner s) -> { return new Greyscale(...); })
    // knownCommands.put("blue-component", (Scanner s) -> { return new Greyscale(...); })
    // knownCommands.put("green-component", (Scanner s) -> { return new Greyscale(...); })
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
