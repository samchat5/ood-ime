package cs3500.ime.controller.commands;

import cs3500.ime.GreyscaleComponent;
import cs3500.ime.model.IIMEModel;

public class GreyScale implements IIMECommand {

  private final GreyscaleComponent component;
  private final String destImageName;
  private final String imageName;

  public GreyScale(String imageName, String destImageName, GreyscaleComponent component) {
    this.imageName = imageName;
    this.destImageName = destImageName;
    this.component = component;
  }

  /**
   * Runs a greyscaling command on the model object.
   *
   * @param model model to run command on
   * @throws IllegalStateException iff the command fails
   */
  @Override
  public void run(IIMEModel model) throws IllegalStateException {
    try {
      model.greyScale(imageName, destImageName, component);
    } catch (Exception e) {
      throw new IllegalStateException(e.getMessage());
    }
  }
}
