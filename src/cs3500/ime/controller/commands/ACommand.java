package cs3500.ime.controller.commands;

import cs3500.ime.model.IIMEModel;
import java.util.function.Consumer;

public abstract class ACommand implements IIMECommand {

  private final Consumer<IIMEModel> func;
  private final String errorMessage;

  protected ACommand(Consumer<IIMEModel> func, String errorMessage) {
    this.func = func;
    this.errorMessage = errorMessage;
  }

  @Override
  public void run(IIMEModel model) throws IllegalStateException {
    try {
      func.accept(model);
    } catch (Exception e) {
      throw new IllegalStateException(errorMessage);
    }
  }
}
