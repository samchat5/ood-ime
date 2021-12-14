package cs3500.ime.controller.commands;

import cs3500.ime.model.IIMEModel;
import java.util.function.Consumer;

/**
 * Abstract class for all commands. Takes a function from each subclass that is called on a model
 * object, and an error message to display to the user if an error occurs.
 */
public abstract class ACommand implements IIMECommand {

  private final Consumer<IIMEModel> func;

  protected ACommand(Consumer<IIMEModel> func) {
    this.func = func;
  }

  @Override
  public void run(IIMEModel model) throws IllegalStateException {
    try {
      func.accept(model);
    } catch (IllegalArgumentException | IllegalStateException | NullPointerException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }
}
